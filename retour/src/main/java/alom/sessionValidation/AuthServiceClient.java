package alom.sessionValidation;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.StringReader;

public class AuthServiceClient {
    private final Client client = ClientBuilder.newClient();
    private final String authServiceUrl = "http://localhost:8080/auth/webapi/auth/validate";

    public String validateToken(String token) {
        try {
            // Appel au microservice Auth
            String jsonResponse = client.target(authServiceUrl)
                                        .queryParam("token", token)
                                        .request(MediaType.APPLICATION_JSON)
                                        .get(String.class);

            // Extraire uniquement la valeur "nickname"
            JsonObject jsonObject = Json.createReader(new StringReader(jsonResponse)).readObject();
            return jsonObject.getString("nickname");

        } catch (Exception e) {
            System.err.println("Error validating token: " + e.getMessage());
            return null;
        }
    }
}
