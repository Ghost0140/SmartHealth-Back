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

    public void registrarPaciente(RegistroDTO dto) {

        Usuario u = new Usuario();

        u.setUsername(dto.getUsername());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setRol("PACIENTE");
        u.setActivo(true);

        repo.save(u);
    }
    
    public void registrarDoctor(RegistroDTO dto) {

        Usuario u = new Usuario();

        u.setUsername(dto.getUsername());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setRol("DOCTOR");
        u.setActivo(true);

        repo.save(u);
    }
    
    public void registrarFarmacia(RegistroDTO dto) {

        Usuario u = new Usuario();

        u.setUsername(dto.getUsername());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setRol("FARMACIA");
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
