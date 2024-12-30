package alom.controller;

import alom.model.ChannelAction;
import alom.model.ChannelMessage;
import alom.config.KafkaConfig;
import jakarta.annotation.PreDestroy;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Path("/channel")
public class ChannelController {
    private static final String TOPIC_NAME = "channel-messages";
    private static final Map<String, Set<String>> channelMembers = new ConcurrentHashMap<>();
    private final KafkaProducer<String, String> producer;

    public ChannelController() {
        this.producer = new KafkaProducer<>(KafkaConfig.getProducerProperties());
    }

    @POST
    @Path("/join")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response joinChannel(ChannelAction action) {
        try {
            Set<String> members = channelMembers.computeIfAbsent(
                    action.getChannel(),
                    k -> Collections.newSetFromMap(new ConcurrentHashMap<>()));

            if (!members.add(action.getUsername())) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"error\": \"User already in channel\"}")
                        .build();
            }

            return Response.ok()
                    .entity("{\"status\": \"User joined channel\"}")
                    .build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity("{\"error\": \"Failed to join channel: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @POST
    @Path("/leave")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response leaveChannel(ChannelAction action) {
        Set<String> members = channelMembers.get(action.getChannel());
        if (members == null || !members.contains(action.getUsername())) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Channel not found or user not in channel\"}")
                    .build();
        }
        members.remove(action.getUsername());
        if (members.isEmpty()) {
            channelMembers.remove(action.getChannel());
        }

        return Response.ok()
                .entity("{\"status\": \"User left channel\"}")
                .build();
    }

    @GET
    @Path("/members")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChannelMembers(@QueryParam("channel") String channel) {
        Set<String> members = channelMembers.get(channel);
        if (members == null || members.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Channel not found or no members\"}")
                    .build();
        }
        return Response.ok(members).build();
    }

    @POST
    @Path("/message")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(ChannelMessage message) {
        Set<String> members = channelMembers.get(message.getChannel());
        if (members == null || members.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Channel not found or no members in channel\"}")
                    .build();
        }

        try {
            String jsonMessage = String.format(
                    "{\"sender\":\"%s\", \"channel\":\"%s\", \"content\":\"%s\"}",
                    message.getSender(), message.getChannel(), message.getContent());

            producer.send(
                    new ProducerRecord<>(TOPIC_NAME, message.getChannel(), jsonMessage),
                    (metadata, exception) -> {
                        if (exception != null) {
                            System.err.println("Error sending message to Kafka: " + exception.getMessage());
                        }
                    });

            return Response.ok()
                    .entity("{\"status\": \"Message sent successfully\"}")
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PreDestroy
    public void cleanup() {
        if (producer != null) {
            producer.close();
            System.out.println("Kafka producer closed successfully");
        }
    }
}
