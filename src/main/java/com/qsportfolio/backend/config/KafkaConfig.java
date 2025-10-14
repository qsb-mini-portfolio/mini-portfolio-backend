package com.qsportfolio.backend.config;

import com.qsportfolio.backend.domain.kafka.KafkaTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic replyTopic() {
        return new NewTopic(KafkaTopic.ReplyTopic.topic, 1, (short) 1);
    }

    @Bean
    public NewTopic healthCheckRequestTopic() {
        return new NewTopic(KafkaTopic.HealthCheckRequest.topic, 1, (short) 1);
    }

    @Bean
    public NewTopic stockPriceGraphRequestTopic() {
        return new NewTopic(KafkaTopic.StockPriceGraphRequest.topic, 1, (short) 1);
    }

    @Bean
    public NewTopic portfolioGraphRequestTopic() {
        return new NewTopic(KafkaTopic.PortfolioGraphRequest.topic, 1, (short) 1);
    }
}