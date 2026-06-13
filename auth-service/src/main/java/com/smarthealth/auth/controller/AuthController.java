package com.smarthealth.auth.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarthealth.auth.dto.LoginDTO;
import com.smarthealth.auth.dto.RegistroDTO;
import com.smarthealth.auth.model.Usuario;
import com.smarthealth.auth.security.JwtUtil;
import com.smarthealth.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
    private AuthService authService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<?> registerPaciente(@RequestBody RegistroDTO dto) {

	    authService.registrarPaciente(dto);
	    return ResponseEntity.status(HttpStatus.CREATED).body("Paciente registrado exitosamente");
	}
	
	@PostMapping("/register-doctor")
	public ResponseEntity<?> registerDoctor(@RequestBody RegistroDTO dto) {

	    authService.registrarDoctor(dto);
	    return ResponseEntity.status(HttpStatus.CREATED).body("Doctor registrado exitosamente");
	}
	
	@PostMapping("/register-farmacia")
	public ResponseEntity<?> registerFarmacia(@RequestBody RegistroDTO dto) {

	    authService.registrarFarmacia(dto);
	    return ResponseEntity.status(HttpStatus.CREATED).body("Usuario de farmacia registrado exitosamente");
	}
	
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody LoginDTO dto) {

	    Usuario user = authService.login(dto);
	    String token = jwtUtil.generateToken(user.getUsername(), user.getRol());

	    Map<String, Object> respuesta = new HashMap<>();

	    respuesta.put("id", user.getIdUsuario());
	    respuesta.put("username", user.getUsername());
	    respuesta.put("rol", user.getRol());
	    respuesta.put("token", token);

	    return respuesta;
	}
    
    @GetMapping("/me")
    public ResponseEntity<?> getMe(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }
}
