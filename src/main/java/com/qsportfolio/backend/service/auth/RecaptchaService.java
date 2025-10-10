package com.qsportfolio.backend.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RecaptchaService {

    @Value("${CAPTCHA_SECRET_KEY}") // variable d’environnement backend
    private String secret;

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verify(String token, String expectedAction) {
        RestTemplate restTemplate = new RestTemplate();

        // Corps de la requête à Google
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("secret", secret);
        body.add("response", token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    VERIFY_URL, HttpMethod.POST, requestEntity, Map.class
            );

            if (response.getBody() == null) {
                System.err.println("⚠️ Recaptcha: réponse vide du serveur Google");
                return false;
            }

            Map<String, Object> result = response.getBody();
            boolean success = (Boolean) result.getOrDefault("success", false);

            if (!success) {
                List<String> errorCodes = (List<String>) result.get("error-codes");
                System.err.println("❌ Recaptcha échoué — codes: " + errorCodes);
                return false;
            }

            // Extraire les infos supplémentaires
            String action = (String) result.get("action");
            Double score = (Double) result.getOrDefault("score", 0.0);

            System.out.printf("✅ Recaptcha OK — action=%s, score=%.2f%n", action, score);

            // Vérifie l’action et le score minimal requis
            if (!expectedAction.equals(action)) {
                System.err.printf("⚠️ Action reCAPTCHA invalide : attendu '%s', reçu '%s'%n",
                        expectedAction, action);
                return false;
            }

            if (score < 0.5) {
                System.err.printf("⚠️ Score trop bas : %.2f (minimum 0.5)%n", score);
                return false;
            }

            return true;

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la vérification reCAPTCHA : " + e.getMessage());
            return false;
        }
    }
}
