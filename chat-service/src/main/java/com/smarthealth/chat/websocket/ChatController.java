package com.smarthealth.chat.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.smarthealth.chat.dto.ChatMessageDTO;
import com.smarthealth.chat.model.MensajeChat;
import com.smarthealth.chat.service.ChatService;

@Controller
public class ChatController {

	@Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/mensaje")
    public void enviarMensaje(ChatMessageDTO dto) {

        MensajeChat guardado =
                chatService.guardarMensaje(dto);

        messagingTemplate.convertAndSend(
                "/topic/" + dto.getReceptor(),
                guardado
        );
    }
}
