package dev.yawkar.auxilium.repository;

import dev.yawkar.auxilium.repository.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
