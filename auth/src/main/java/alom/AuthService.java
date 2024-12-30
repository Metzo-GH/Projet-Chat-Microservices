package alom;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import alom.model.User;
import alom.model.TokenResponse;
import alom.model.UserResponse;

@Path("/auth")
public class AuthService {
    private static final Map<String, String> users = new ConcurrentHashMap<>(); // nickname -> password
    private static final Map<String, String> tokens = new ConcurrentHashMap<>(); // token -> nickname

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        if (users.containsKey(user.getNickname())) {
            return Response.status(Response.Status.CONFLICT).entity("User already exists").build();
        }
        users.put(user.getNickname(), user.getPassword());
        return Response.ok("User registered successfully").build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        if (!users.containsKey(user.getNickname()) || !users.get(user.getNickname()).equals(user.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }
        String token = UUID.randomUUID().toString();
        tokens.put(token, user.getNickname());
        return Response.ok(new TokenResponse(token)).build();
    }

    @GET
    @Path("/validate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validate(@QueryParam("token") String token) {
        String nickname = tokens.get(token);
        if (nickname == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }
        return Response.ok(new UserResponse(nickname)).build();
    }
}
