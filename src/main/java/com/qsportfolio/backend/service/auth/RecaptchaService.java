package com.qsportfolio.backend.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class RecaptchaService {

    @Value("${CAPTCHA_SECRET_KEY}") // Variable d'environnement côté backend
    private String secret;

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verify(String token, String action) {
        RestTemplate restTemplate = new RestTemplate();

        // Préparer le corps de la requête
        Map<String, String> body = Map.of(
                "secret", secret,
                "response", token
        );

        // Envoyer la requête POST à Google
        Map response = restTemplate.postForObject(VERIFY_URL, body, Map.class);

        if (response == null || !Boolean.TRUE.equals(response.get("success"))) {
            return false;
        }

        // Vérifier l’action et le score (reCAPTCHA v3)
        Double score = (Double) response.get("score");
        String responseAction = (String) response.get("action");

        return "register".equals(responseAction) && score != null && score >= 0.5;
    }
}
