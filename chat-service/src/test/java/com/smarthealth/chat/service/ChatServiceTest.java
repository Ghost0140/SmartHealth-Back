package com.smarthealth.chat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.smarthealth.chat.dto.ChatMessageDTO;
import com.smarthealth.chat.model.MensajeChat;
import com.smarthealth.chat.repository.MensajeChatRepository;

class ChatServiceTest {

    @Test
    void guardaYPublicaAvisoDelSistemaEnElCanalDelReceptor() {
        MensajeChatRepository repository = mock(MensajeChatRepository.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);
        when(repository.save(org.mockito.ArgumentMatchers.any(MensajeChat.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ChatService service = new ChatService();
        ReflectionTestUtils.setField(service, "repository", repository);
        ReflectionTestUtils.setField(service, "messagingTemplate", messagingTemplate);

        MensajeChat result = service.guardarMensajeSistema(
                new ChatMessageDTO(null, "doctor", "Nueva cita programada"));

        assertEquals("Sistema", result.getEmisor());
        assertEquals("doctor", result.getReceptor());
        assertEquals("Nueva cita programada", result.getContenido());
        verify(repository).save(result);
        verify(messagingTemplate).convertAndSend(eq("/topic/doctor"), eq(result));
    }
}
