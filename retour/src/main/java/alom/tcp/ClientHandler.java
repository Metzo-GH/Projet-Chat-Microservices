package alom.tcp;

import java.io.*;
import java.net.Socket;
import java.util.Map;

import alom.sessionValidation.AuthServiceClient;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final AuthServiceClient authServiceClient;
    private final Map<String, Socket> userSockets;
    private String nickname;

    public ClientHandler(Socket clientSocket, AuthServiceClient authServiceClient, Map<String, Socket> userSockets) {
        this.clientSocket = clientSocket;
        this.authServiceClient = authServiceClient;
        this.userSockets = userSockets;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Demander le token au client
            out.println("Welcome to the server. Please enter your token:");
            String token = in.readLine();

            // Valider le token via AuthServiceClient
            nickname = authServiceClient.validateToken(token);
            if (nickname == null) {
                out.println("Invalid token. Disconnecting...");
                clientSocket.close();
                return;
            }

            // Ajouter le socket à la map partagée
            userSockets.put(nickname, clientSocket);

            // Informer le client de la connexion réussie
            out.println("Connected as " + nickname);
            System.out.println("User " + nickname + " connected.");

            // Boucle pour maintenir la connexion ouverte tant que le client n'envoie pas "quit"
            String message;
            while (!clientSocket.isClosed()) {
                try {
                    if ((message = in.readLine()) != null) {
                        if (message.equalsIgnoreCase("quit")) {
                            break; // Quitter la boucle si le client envoie "quit"
                        }

                        System.out.println("Received from " + nickname + ": " + message);
                        out.println("Echo: " + message); // Répondre au client
                    } else {
                        // Si le client ferme la connexion de son côté
                        System.out.println("Client " + nickname + " disconnected.");
                        break;
                    }
                } catch (IOException e) {
                    System.err.println("Error reading message from client " + nickname + ": " + e.getMessage());
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    private void disconnect() {
        try {
            userSockets.remove(nickname); // Supprimer l'utilisateur de la map des sockets
            clientSocket.close();
            System.out.println("User " + nickname + " disconnected.");
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
    }
}
