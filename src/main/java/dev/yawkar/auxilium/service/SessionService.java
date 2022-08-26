package dev.yawkar.auxilium.service;

import dev.yawkar.auxilium.repository.SessionRepository;
import dev.yawkar.auxilium.repository.entity.Session;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session getSessionById(long sessionId) {
        return sessionRepository.findById(sessionId).get();
    }

    public Session updateOrSaveSession(Session session) {
        return sessionRepository.save(session);
    }

    public void deleteSessionById(long sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
