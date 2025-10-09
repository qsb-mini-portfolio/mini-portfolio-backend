package com.qsportfolio.backend.service.demo;

import com.qsportfolio.backend.domain.kafka.KafkaTopic;
import com.qsportfolio.backend.service.auth.AuthService;
import com.qsportfolio.backend.service.kafka.KafkaService;
import com.qsportfolio.backend.service.transaction.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    /*
        Service for demo purpose. DO NOT USE OTHERWISE !!!
     */
    private final TransactionService transactionService;
    private final AuthService authService;
    private final KafkaService kafkaService;

    public DemoService(TransactionService transactionService,
                       AuthService authService,
                       KafkaService kafkaService) {
        this.transactionService = transactionService;
        this.authService = authService;
        this.kafkaService = kafkaService;
    }

    public String demoLogin() {
        try {
            authService.createUser("demo", "demoPassword", "demo@email");
        } catch (Exception e) {
            // Do nothing
        }
        String token = authService.login("demo", "demoPassword");
        transactionService.deleteAllTransactionForDemo();
        return token;
    }

    public String pythonHealthCheck() {
        try {
            kafkaService.sendAndReceive(KafkaTopic.HealthCheckRequest.topic, "", 5000);
        } catch (Exception e) {
            return "Down";
        }
        return "Up";
    }

}
