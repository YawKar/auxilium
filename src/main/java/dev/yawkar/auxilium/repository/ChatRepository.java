package dev.yawkar.auxilium.repository;

import dev.yawkar.auxilium.context.ContextType;
import dev.yawkar.auxilium.repository.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findAllByContextTypeAndReadyToHelp(ContextType contextType, boolean readyToHelp);
}
