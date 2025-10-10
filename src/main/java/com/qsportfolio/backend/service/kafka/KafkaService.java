package com.qsportfolio.backend.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qsportfolio.backend.domain.kafka.KafkaTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Map<String, CompletableFuture<String>> pendingRequests = new ConcurrentHashMap<>();

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends a message to the given request topic and waits for a reply on the shared reply topic.
     */
    public String sendAndReceive(String requestTopic, String request, long timeoutMs) throws Exception {
        String correlationId = UUID.randomUUID().toString();

        ProducerRecord<String, String> record = new ProducerRecord<>(requestTopic, request);
        record.headers().add("correlationId", correlationId.getBytes(StandardCharsets.UTF_8));

        CompletableFuture<String> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);

        kafkaTemplate.send(record);

        try {
            return future.get(timeoutMs, TimeUnit.MILLISECONDS);
        } finally {
            pendingRequests.remove(correlationId);
        }
    }

    @KafkaListener(topics = "reply-topic")
    public void listenReplies(ConsumerRecord<String, String> record) {
        Header header = record.headers().lastHeader("correlationId");
        if (header == null) {
            System.err.println("Received message without correlationId: " + record.value());
            return;
        }

        String correlationId = new String(header.value(), StandardCharsets.UTF_8);

        CompletableFuture<String> future = pendingRequests.remove(correlationId);
        if (future != null) {
            String value = record.value();
            if (value.startsWith("\"") && value.endsWith("\"")) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    value = mapper.readValue(value, String.class);
                } catch (Exception e) {
                    System.err.println("Failed to unwrap double-encoded JSON: " + e.getMessage());
                }
            }

            future.complete(value);
        } else {
            System.err.println("No pending request for correlationId: " + correlationId);
        }
    }
}
