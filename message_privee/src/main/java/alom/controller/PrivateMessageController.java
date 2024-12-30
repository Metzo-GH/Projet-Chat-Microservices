package alom.controller;

import alom.model.Message;
import alom.config.KafkaConfig;
import jakarta.annotation.PreDestroy;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/private-messages")
public class PrivateMessageController {
    private static final String TOPIC_NAME = "private-messages";
    private final KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper;

    public PrivateMessageController() {
        this.producer = new KafkaProducer<>(KafkaConfig.getProducerProperties());
        this.objectMapper = new ObjectMapper();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(Message message) {
        try {
            validateMessage(message);
            String jsonMessage = objectMapper.writeValueAsString(message);
            sendToKafka(message.getRecipient(), jsonMessage);
            return Response.ok().entity("{\"status\": \"Message sent successfully\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Error processing message\"}")
                    .build();
        }
    }

    private void validateMessage(Message message) {
        if (message.getSender() == null || message.getSender().trim().isEmpty() ||
                message.getRecipient() == null || message.getRecipient().trim().isEmpty() ||
                message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Sender, recipient, and content must not be empty");
        }
    }

    private void sendToKafka(String key, String value) {
        producer.send(new ProducerRecord<>(TOPIC_NAME, key, value),
                (metadata, exception) -> {
                    if (exception != null) {
                        System.err.println("Error sending message to Kafka: " + exception.getMessage());
                    }
                });
    }

    @PreDestroy
    public void cleanup() {
        if (producer != null) {
            producer.close();
            System.out.println("Kafka producer closed successfully");
        }
    }

}