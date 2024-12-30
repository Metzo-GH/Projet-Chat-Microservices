package alom.tcp;

import alom.service.KafkaToSocketConsumer;
import alom.sessionValidation.AuthServiceClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer implements Runnable {

    private final int port;
    private ServerSocket serverSocket;
    private boolean running;
    private final ExecutorService threadPool;
    private final AuthServiceClient authServiceClient;
    private final Map<String, Socket> userSockets = new ConcurrentHashMap<>();
    private KafkaToSocketConsumer kafkaConsumer;

    public SocketServer(int port) {
        this.port = port;
        this.threadPool = Executors.newCachedThreadPool();
        this.authServiceClient = new AuthServiceClient();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            System.out.println("SocketServer started on port " + port);

            // Démarrer le consommateur Kafka
            kafkaConsumer = new KafkaToSocketConsumer(userSockets);
            new Thread(kafkaConsumer).start();

            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress());
                threadPool.execute(new ClientHandler(clientSocket, authServiceClient, userSockets));
            }
        } catch (IOException e) {
            if (running) {
                System.err.println("Error in SocketServer: " + e.getMessage());
            }
        } finally {
            stopServer();
        }
    }

    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            threadPool.shutdownNow();
            if (kafkaConsumer != null) {
                kafkaConsumer.stop();
            }
            System.out.println("SocketServer stopped.");
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
        }
    }
}
