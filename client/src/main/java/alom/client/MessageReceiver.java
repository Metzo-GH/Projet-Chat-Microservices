package alom.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageReceiver implements Runnable {
    private final Socket socket;
    private final BufferedReader reader;
    private volatile boolean running = true;

    public MessageReceiver(String token) throws Exception {
        this.socket = new Socket("localhost", 12345);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        // Envoyer le token pour authentification
        var writer = socket.getOutputStream();
        writer.write((token + "\n").getBytes());
        writer.flush();
    }

    @Override
    public void run() {
        try {
            String message;
            while (running && (message = reader.readLine()) != null) {
                System.out.println("\n" + message);
                System.out.print("Votre choix : "); // Réafficher le prompt
            }
        } catch (Exception e) {
            if (running) {
                System.err.println("Erreur de réception: " + e.getMessage());
            }
        }
    }

    public void stop() {
        running = false;
        try {
            socket.close();
        } catch (Exception e) {
            System.err.println("Erreur lors de la fermeture: " + e.getMessage());
        }
    }
}
