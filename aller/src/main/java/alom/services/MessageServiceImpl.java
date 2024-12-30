package alom.services;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import alom.clients.AuthServiceClient;

@WebService(endpointInterface = "alom.services.MessageService")
public class MessageServiceImpl implements MessageService {

    private AuthServiceClient authClient;
    private static final String AUTH_BASE_URL = "http://localhost:8080/auth/webapi/auth";
    private ObjectMapper objectMapper = new ObjectMapper();

    // Constructeur vide par défaut
    public MessageServiceImpl() {
        this.authClient = new AuthServiceClient();
    }

    @WebMethod
    @Override
    public String sendPrivateMessage(
        @WebParam(name = "token") String token,
        @WebParam(name = "recipient") String recipient,
        @WebParam(name = "message") String message) {
        ensureInitialized();
        String senderJson = authClient.validateToken(token);
        if (senderJson == null) {
            return "Invalid token. Access denied.";
        }

        // Extraire le nickname du JSON retourné par le service d'authentification
        String sender;
        try {
            JsonNode jsonNode = objectMapper.readTree(senderJson);
            sender = jsonNode.get("nickname").asText();
        } catch (Exception e) {
            return "Error processing user information";
        }

        String privateMessageServiceUrl = "http://localhost:8080/message_privee/webapi/private-messages";
        return forwardToService(privateMessageServiceUrl, Map.of(
                "sender", sender,
                "recipient", recipient,
                "content", message));
    }

    @Override
    public String sendChannelMessage(
        @WebParam(name = "token") String token,
        @WebParam(name = "channel") String channel,
        @WebParam(name = "message") String message) {
        ensureInitialized();
        String senderJson = authClient.validateToken(token);
        if (senderJson == null) {
            return "Invalid token. Access denied.";
        }

        // Extraire le nickname du JSON
        String sender;
        try {
            JsonNode jsonNode = objectMapper.readTree(senderJson);
            sender = jsonNode.get("nickname").asText();
        } catch (Exception e) {
            return "Error processing user information";
        }

        String channelMessageServiceUrl = "http://localhost:8080/message_channel/webapi/channel/message";
        return forwardToService(channelMessageServiceUrl, Map.of(
                "sender", sender, // Utiliser le nickname extrait
                "channel", channel,
                "content", message));
    }

    @WebMethod
    @Override
    public String joinChannel(String token, String channel) {
        ensureInitialized();
        String userJson = authClient.validateToken(token);
        if (userJson == null) {
            return "Invalid token. Access denied.";
        }
    
        // Extraire le nickname du JSON
        String username;
        try {
            JsonNode jsonNode = objectMapper.readTree(userJson);
            username = jsonNode.get("nickname").asText();
        } catch (Exception e) {
            return "Error processing user information";
        }
    
        String joinChannelServiceUrl = "http://localhost:8080/message_channel/webapi/channel/join";
        return forwardToService(joinChannelServiceUrl, Map.of(
                "username", username,  // Utiliser "username" au lieu de "sender"
                "channel", channel));
    }

    @WebMethod
    @Override
    public String leaveChannel(
            @WebParam(name = "token") String token,
            @WebParam(name = "channel") String channel) {

        ensureInitialized(); // S'assurer que l'objet est initialisé
        String username = authClient.validateToken(token);
        if (username == null) {
            return "Invalid token. Access denied.";
        }

        // Extraire le nickname du JSON
        try {
            JsonNode jsonNode = objectMapper.readTree(username);
            username = jsonNode.get("nickname").asText();
        } catch (Exception e) {
            return "Error processing user information";
        }

        String leaveChannelServiceUrl = "http://localhost:8080/message_channel/webapi/channel/leave";
        return forwardToService(leaveChannelServiceUrl, Map.of(
                "username", username,
                "channel", channel));
    }

    @WebMethod
    @Override
    public String register(
            @WebParam(name = "nickname") String nickname,
            @WebParam(name = "password") String password) {
        Client client = ClientBuilder.newClient();
        try {
            // Préparer les données d'enregistrement
            String json = String.format("{\"nickname\":\"%s\", \"password\":\"%s\"}", nickname, password);

            // Appeler l'endpoint REST pour l'enregistrement
            Response response = client.target(AUTH_BASE_URL + "/register")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(json));

            if (response.getStatus() == 200) {
                return "User registered successfully.";
            } else {
                return "Failed to register: " + response.readEntity(String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during registration: " + e.getMessage();
        }
    }

    @WebMethod
    @Override
    public String login(
            @WebParam(name = "nickname") String nickname,
            @WebParam(name = "password") String password) {
        Client client = ClientBuilder.newClient();
        try {
            // Préparer les données de connexion
            String json = String.format("{\"nickname\":\"%s\", \"password\":\"%s\"}", nickname, password);

            // Appeler l'endpoint REST pour la connexion
            Response response = client.target(AUTH_BASE_URL + "/login")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(json));

            if (response.getStatus() == 200) {
                return response.readEntity(String.class); // Retourner le token
            } else {
                return "Failed to login: " + response.readEntity(String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during login: " + e.getMessage();
        }
    }

    @WebMethod
    @Override
    public String validateToken(String token) {
        Client client = ClientBuilder.newClient();
        try {
            // Appeler l'endpoint REST pour valider le token
            Response response = client.target(AUTH_BASE_URL + "/validate")
                    .queryParam("token", token)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == 200) {
                return response.readEntity(String.class); // Retourner le nickname
            } else {
                return "Invalid token: " + response.readEntity(String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during token validation: " + e.getMessage();
        }
    }

    private void ensureInitialized() {
        if (authClient == null) {
            throw new IllegalStateException("MessageServiceImpl is not initialized. Call initialize() first.");
        }
    }

    private String forwardToService(String serviceUrl, Map<String, String> requestBody) {
        Client client = ClientBuilder.newClient();
        try {
            // Convertir la Map en JSON String
            String jsonBody = objectMapper.writeValueAsString(requestBody);

            Response response = client.target(serviceUrl)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(jsonBody));

            if (response.getStatus() == 200) {
                return "Operation successful.";
            } else {
                return "Failed operation: " + response.readEntity(String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during operation: " + e.getMessage();
        }
    }
}
