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

    @Value("${CAPTCHA_SECRET_KEY}")
    private String secret;

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verify(String token ) {
        RestTemplate restTemplate = new RestTemplate();

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
                return false;
            }

            Map<String, Object> result = response.getBody();
            return (boolean) (Boolean) result.getOrDefault("success", false);

        } catch (Exception e) {
            return false;
        }
    }
}
