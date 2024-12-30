package alom.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import alom.config.KafkaConfig;
import alom.model.Message;

import java.net.Socket;
import java.time.Duration;
import java.util.*;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.StringReader;

public class KafkaToSocketConsumer implements Runnable {
    private final KafkaConsumer<String, String> consumer;
    private final MessageHandler messageHandler;
    private volatile boolean running = true;

    public KafkaToSocketConsumer(Map<String, Socket> userSockets) {
        this.consumer = new KafkaConsumer<>(KafkaConfig.getConsumerProps());
        this.consumer.subscribe(Arrays.asList("private-messages", "channel-messages"));
        this.messageHandler = new MessageHandler(userSockets);
    }

    @Override
    public void run() {
        try {
            while (running) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(this::processRecord);
            }
        } finally {
            consumer.close();
            stop();
        }
    }

    private void processRecord(ConsumerRecord<String, String> record) {
        try {
            JsonObject messageObj = Json.createReader(new StringReader(record.value())).readObject();
            Message message = new Message(
                    messageObj.getString("sender"),
                    messageObj.getString("content"),
                    record.key());

            if (record.topic().equals("private-messages")) {
                messageHandler.handlePrivateMessage(message);
            } else if (record.topic().equals("channel-messages")) {
                messageHandler.handleChannelMessage(message);
            }
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    public void stop() {
        running = false;
    }
}
