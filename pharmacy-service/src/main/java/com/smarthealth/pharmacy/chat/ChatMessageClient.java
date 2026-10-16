package com.smarthealth.pharmacy.chat;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "chat-service")
public interface ChatMessageClient {

    @PostMapping("/chat/mensajes/sistema")
    void publicarMensajeSistema(
            @RequestHeader("X-Internal-Token") String token,
            @RequestBody ChatSystemMessage message);
}
