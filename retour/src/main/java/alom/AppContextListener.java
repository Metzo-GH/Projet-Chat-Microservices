package alom;

import alom.tcp.SocketServer;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    private SocketServer socketServer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Starting SocketServer...");
        socketServer = new SocketServer(12345);
        new Thread(socketServer).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Stopping SocketServer...");
        if (socketServer != null) {
            socketServer.stopServer();
        }
    }
}
