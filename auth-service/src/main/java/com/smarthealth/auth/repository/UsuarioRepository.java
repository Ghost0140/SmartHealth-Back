package com.smarthealth.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smarthealth.auth.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

	Optional<UsuarioEntity> findByCorreo(String correo);

	boolean existsByCorreo(String correo);

	boolean existsByCorreoAndIdUsuarioNot(String correo, Integer idUsuario);
	
	boolean existsByIdDoctor(Integer idDoctor);
	
	@Query("""
			SELECT u
			FROM UsuarioEntity u
			JOIN FETCH u.rol
	""")
	List<UsuarioEntity> findAllWithRol();
	
	@Query("""
			SELECT u
			FROM UsuarioEntity u
			JOIN FETCH u.rol
			WHERE u.idUsuario = :id
	""")
	Optional<UsuarioEntity> findByIdWithRol(Integer id);
	
	@Query("""
		    SELECT u
		    FROM UsuarioEntity u
		    JOIN FETCH u.rol
		    WHERE u.activo = :activo
	""")
	List<UsuarioEntity> findByActivoWithRol(Boolean activo);

}
