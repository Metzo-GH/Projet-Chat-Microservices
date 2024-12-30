package alom.client;

import jakarta.xml.ws.Service;
import javax.xml.namespace.QName;

import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import alom.services.MessageService;

public class ChatClient {
    private static MessageService messageService;
    private static Scanner scanner;
    private static String currentToken;
    private static MessageReceiver messageReceiver;
    private static Thread receiverThread;

    public static void main(String[] args) {
        try {
            // Initialisation du client SOAP
            URL wsdlURL = new URI("http://localhost:8080/aller/MessageService?wsdl").toURL();
            QName serviceName = new QName("http://services.alom/", "MessageServiceImplService");
            
            Service service = Service.create(wsdlURL, serviceName);
            messageService = service.getPort(MessageService.class);
            scanner = new Scanner(System.in);
            
            while (true) {
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        register();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        sendPrivateMessage();
                        break;
                    case 4:
                        sendChannelMessage();
                        break;
                    case 5:
                        joinChannel();
                        break;
                    case 6:
                        leaveChannel();
                        break;
                    case 0:
                        cleanup();
                        System.out.println("Au revoir!");
                        return;
                    default:
                        System.out.println("Choix invalide!");
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation du client: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private static void showMenu() {
        System.out.println("\n=== Menu Principal ===");
        System.out.println("1. S'inscrire");
        System.out.println("2. Se connecter");
        System.out.println("3. Envoyer un message privé");
        System.out.println("4. Envoyer un message dans un canal");
        System.out.println("5. Rejoindre un canal");
        System.out.println("6. Quitter un canal");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }

    private static void register() {
        System.out.print("Entrez votre pseudo : ");
        String nickname = scanner.nextLine();
        System.out.print("Entrez votre mot de passe : ");
        String password = scanner.nextLine();

        String response = messageService.register(nickname, password);
        System.out.println(response);
    }

    private static void login() {
        System.out.print("Entrez votre pseudo : ");
        String nickname = scanner.nextLine();
        System.out.print("Entrez votre mot de passe : ");
        String password = scanner.nextLine();

        String response = messageService.login(nickname, password);
        if (!response.startsWith("Failed") && !response.startsWith("Error")) {
            currentToken = response;
            String tcptoken = currentToken.replace("{\"token\":\"", "").replace("\"}", "");
            System.out.println("Connexion réussie!"+tcptoken);
            
            // Démarrer le receiver après login réussi
            try {
                
                messageReceiver = new MessageReceiver(tcptoken);
                receiverThread = new Thread(messageReceiver);
                receiverThread.start();
            } catch (Exception e) {
                System.err.println("Erreur lors de la connexion au serveur de messages: " + e.getMessage());
            }
        } else {
            System.out.println(response);
        }
    }

    private static void sendPrivateMessage() {
        if (!checkAuthentication()) return;

        System.out.print("Destinataire : ");
        String recipient = scanner.nextLine();
        System.out.print("Message : ");
        String message = scanner.nextLine();

        String response = messageService.sendPrivateMessage(currentToken, recipient, message);
        System.out.println(response);
    }

    private static void sendChannelMessage() {
        if (!checkAuthentication()) return;

        System.out.print("Nom du canal : ");
        String channel = scanner.nextLine();
        System.out.print("Message : ");
        String message = scanner.nextLine();

        String response = messageService.sendChannelMessage(currentToken, channel, message);
        System.out.println(response);
    }

    private static void joinChannel() {
        if (!checkAuthentication()) return;

        System.out.print("Nom du canal à rejoindre : ");
        String channel = scanner.nextLine();

        String response = messageService.joinChannel(currentToken, channel);
        System.out.println(response);
    }

    private static void leaveChannel() {
        if (!checkAuthentication()) return;

        System.out.print("Nom du canal à quitter : ");
        String channel = scanner.nextLine();

        String response = messageService.leaveChannel(currentToken, channel);
        System.out.println(response);
    }

    private static boolean checkAuthentication() {
        if (currentToken == null) {
            System.out.println("Vous devez d'abord vous connecter!");
            return false;
        }
        return true;
    }

    private static void cleanup() {
        if (messageReceiver != null) {
            messageReceiver.stop();
        }
        if (receiverThread != null) {
            try {
                receiverThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}