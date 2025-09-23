package com.backend.authservice.kafka;

import com.backend.authservice.dto.response.UserResponse;
import com.backend.authservice.entities.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishUserRegister(Users userResponse) {
        try{
            Map<String, Object> event = new HashMap<>();
            event.put("event", "USER_REGISTERED");
            event.put("timestamp", Instant.now().toString());
            event.put("data", userResponse);

            String message =  objectMapper.writeValueAsString(event);
            kafkaTemplate.send("user.evenets", message);
            System.out.println("PUBLISHED USER_REGISTERED event: " + message);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void publishUserLogin(Users userResponse) {
        try{
            Map<String, Object> event = new HashMap<>();
            event.put("event", "USER_LOGIN");
            event.put("timestamp", Instant.now().toString());
            event.put("data", userResponse);

            String message =  objectMapper.writeValueAsString(event);
            kafkaTemplate.send("user.login", message);
            System.out.println("PUBLISHED USER_LOGIN event: " + message);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
