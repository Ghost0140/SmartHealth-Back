package com.smarthealth.chat.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.smarthealth.chat.dto.ChatMessageDTO;
import com.smarthealth.chat.model.MensajeChat;
import com.smarthealth.chat.repository.MensajeChatRepository;

@Service
public class ChatService {

	@Autowired
    private MensajeChatRepository repository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public MensajeChat guardarMensaje(ChatMessageDTO dto) {
		if (dto.getEmisor() == null || dto.getEmisor().isBlank()
				|| dto.getReceptor() == null || dto.getContenido() == null
				|| dto.getContenido().isBlank() || dto.getContenido().length() > 1000) {
			throw new IllegalArgumentException("El mensaje requiere emisor, destinatario y contenido de hasta 1000 caracteres");
		}
		if (!dto.getReceptor().matches("admin|doctor:[1-9][0-9]*|recepcionista:[^\\s/]+@[^\\s/]+")) {
			throw new IllegalArgumentException("El destinatario del chat no es válido");
		}
		if (dto.getEmisor().equals(dto.getReceptor())) {
			throw new IllegalArgumentException("No puedes enviarte mensajes a ti mismo");
		}

    	MensajeChat mensaje = new MensajeChat();

        mensaje.setEmisor(dto.getEmisor());
        mensaje.setReceptor(dto.getReceptor());
        mensaje.setContenido(dto.getContenido());
        mensaje.setFecha(LocalDateTime.now());

        return repository.save(mensaje);
    }

    public MensajeChat guardarMensajeSistema(ChatMessageDTO dto) {
        MensajeChat mensaje = new MensajeChat();
        mensaje.setEmisor("Sistema");
        mensaje.setReceptor(dto.getReceptor());
        mensaje.setContenido(dto.getContenido());
        mensaje.setFecha(LocalDateTime.now());

        MensajeChat guardado = repository.save(mensaje);
        messagingTemplate.convertAndSend("/topic/" + guardado.getReceptor(), guardado);
        return guardado;
    }

    public List<MensajeChat> listarMensajes() {
        return repository.findAll();
    }

    public List<MensajeChat> listarMensajesUsuario(String usuario, String rol) {
        return repository.findConversacionUsuario(usuario, rol);
    }
}
