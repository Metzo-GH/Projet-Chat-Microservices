package alom.clients;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;

public class AuthServiceClient {
    private final Client client;
    private final String authServiceUrl = "http://localhost:8080/auth/webapi/auth/validate";

    public AuthServiceClient() {
        this.client = ClientBuilder.newClient();
    }

    public String validateToken(String token) {
        try {
            // Supprimer les accolades et les guillemets si présents
            token = token.replace("{", "").replace("}", "").replace("\"", "");
            if (token.contains("token:")) {
                token = token.split(":")[1].trim();
            }
            
            return client.target(authServiceUrl)
                        .queryParam("token", token)
                        .request(MediaType.APPLICATION_JSON)
                        .get(String.class);
        } catch (Exception e) {
            System.err.println("Error validating token: " + e.getMessage());
            return null;
        }
    }
}