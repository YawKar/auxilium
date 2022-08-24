package dev.yawkar.auxilium.service;

import dev.yawkar.auxilium.context.ContextType;
import dev.yawkar.auxilium.repository.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ContextType getContextType(long chatId) {
        return chatRepository.findById(chatId).get().getContextType();
    }
}
