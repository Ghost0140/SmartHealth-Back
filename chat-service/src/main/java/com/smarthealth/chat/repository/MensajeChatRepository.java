package com.smarthealth.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smarthealth.chat.model.MensajeChat;

public interface MensajeChatRepository extends JpaRepository<MensajeChat, Long>{

	List<MensajeChat> findByEmisorAndReceptor(
            String emisor,
            String receptor);

    List<MensajeChat> findByReceptor(String receptor);
}
