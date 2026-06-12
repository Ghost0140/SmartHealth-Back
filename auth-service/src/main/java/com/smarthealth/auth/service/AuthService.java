package com.smarthealth.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smarthealth.auth.dto.LoginDTO;
import com.smarthealth.auth.dto.RegistroDTO;
import com.smarthealth.auth.model.Usuario;
import com.smarthealth.auth.repository.UsuarioRepository;
import com.smarthealth.auth.security.JwtUtil;

@Service
public class AuthService {

	@Autowired
    private UsuarioRepository repo;
	
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registrar(RegistroDTO dto) {
        Usuario u = new Usuario();
        u.setUsername(dto.getUsername());    
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setRol(dto.getRol());
        u.setActivo(true);
        
        repo.save(u);
    }
    
    public Usuario login(LoginDTO dto) {
        Usuario user = repo.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return user;
    }
}
