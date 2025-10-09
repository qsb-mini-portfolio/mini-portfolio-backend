package com.qsportfolio.backend.domain.kafka;

import com.qsportfolio.backend.domain.stock.StockType;

public enum KafkaTopic {

    ReplyTopic("reply-topic"),
    HealthCheckRequest("health-check-request"),
    HealthCheckResponse("health-check-response");

    public final String topic;

    KafkaTopic(String topic) {
        this.topic = topic;
    }
}
