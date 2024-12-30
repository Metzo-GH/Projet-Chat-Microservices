package alom.service;

import alom.model.Message;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.*;
import java.net.Socket;
import java.io.PrintWriter;

public class MessageHandler {
    private final Map<String, Socket> userSockets;
    private final Client client;

    public MessageHandler(Map<String, Socket> userSockets) {
        this.userSockets = userSockets;
        this.client = ClientBuilder.newClient();
    }

    public void handlePrivateMessage(Message message) {
        sendMessageToUser(message.getRecipient(), message.getSender(), message.getContent());
    }

    public void handleChannelMessage(Message message) {
        List<String> members = fetchChannelMembers(message.getRecipient());
        if (members != null && !members.isEmpty()) {
            members.forEach(member -> sendMessageToUser(member, message.getSender(), message.getContent()));
            System.out.println("Message sent to channel " + message.getRecipient() + " with members: " + members);
        } else {
            System.out.println("No members in channel " + message.getRecipient() + " or channel does not exist.");
        }
    }

    private void sendMessageToUser(String recipient, String sender, String content) {
        Socket recipientSocket = userSockets.get(recipient);
        if (recipientSocket != null && !recipientSocket.isClosed()) {
            try {
                PrintWriter out = new PrintWriter(recipientSocket.getOutputStream(), true);
                out.println("from " + sender + ": " + content);
                System.out.println("Message sent to recipient: " + recipient);
            } catch (Exception e) {
                System.err.println("Error sending message to recipient " + recipient + ": " + e.getMessage());
            }
        } else {
            System.out.println("Recipient " + recipient + " is not connected or socket is closed.");
        }
    }

    private List<String> fetchChannelMembers(String channel) {
        try {
            String channelServiceUrl = "http://localhost:8080/message_channel/webapi/channel/members";
            Response response = client.target(channelServiceUrl)
                    .queryParam("channel", channel)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == 200) {
                List<String> members = response.readEntity(new GenericType<List<String>>() {
                });
                System.out.println("Fetched members for channel " + channel + ": " + members);
                return members;
            } else {
                System.err.println(
                        "Error fetching members for channel " + channel + ": " + response.readEntity(String.class));
            }
        } catch (Exception e) {
            System.err.println("Error fetching channel members: " + e.getMessage());
        }
        return List.of();
    }
}
