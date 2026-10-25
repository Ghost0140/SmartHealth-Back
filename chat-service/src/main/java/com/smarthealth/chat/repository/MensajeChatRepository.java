package com.smarthealth.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.smarthealth.chat.model.MensajeChat;

public interface MensajeChatRepository extends JpaRepository<MensajeChat, Long>{

	List<MensajeChat> findByEmisorAndReceptor(
            String emisor,
            String receptor);

    List<MensajeChat> findByReceptor(String receptor);

	List<MensajeChat> findByReceptorOrderByFechaAsc(String receptor);

	@Query("""
			SELECT m FROM MensajeChat m
			WHERE m.emisor = :usuario OR m.receptor = :usuario
			   OR (m.emisor = 'Sistema' AND m.receptor = :rol)
			ORDER BY m.fecha ASC
			""")
	List<MensajeChat> findConversacionUsuario(
			@Param("usuario") String usuario,
			@Param("rol") String rol);
}
