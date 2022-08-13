package dev.yawkar.auxilium.repository;

import dev.yawkar.auxilium.repository.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
