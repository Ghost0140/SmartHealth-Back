package com.smarthealth.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smarthealth.chat.model.MensajeChat;
import com.smarthealth.chat.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatRestController {

	@Autowired
    private ChatService service;

    @GetMapping("/mensajes")
    public List<MensajeChat> listarMensajes() {
        return service.listarMensajes();
    }
}
