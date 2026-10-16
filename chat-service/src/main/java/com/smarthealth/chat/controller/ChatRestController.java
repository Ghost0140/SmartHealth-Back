package com.smarthealth.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smarthealth.chat.dto.ChatMessageDTO;
import com.smarthealth.chat.model.MensajeChat;
import com.smarthealth.chat.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatRestController {

	@Value("${smarthealth.chat.internal-token:}")
	private String internalToken;

	@Autowired
    private ChatService service;

    @GetMapping("/mensajes")
    public List<MensajeChat> listarMensajes(@RequestParam(required = false) String usuario) {
        return usuario == null || usuario.isBlank()
                ? service.listarMensajes()
                : service.listarMensajesRecibidos(usuario);
    }

    @PostMapping("/mensajes/sistema")
    public ResponseEntity<MensajeChat> publicarMensajeSistema(
            @RequestHeader(value = "X-Internal-Token", required = false) String token,
            @RequestBody ChatMessageDTO dto) {
        if (internalToken.isBlank() || token == null || !internalToken.equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (dto.getReceptor() == null || dto.getReceptor().isBlank()
                || dto.getContenido() == null || dto.getContenido().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardarMensajeSistema(dto));
    }
}
