package dev.yawkar.auxilium.service;

import dev.yawkar.auxilium.context.ContextType;
import dev.yawkar.auxilium.repository.ChatRepository;
import dev.yawkar.auxilium.repository.entity.Chat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    private void registerNewChat(long chatId) {
        Chat chat = new Chat()
                .setId(chatId)
                .setContextType(ContextType.NEW);
        chatRepository.save(chat);
    }

    public ContextType getContextType(long chatId) {
        if (!chatRepository.existsById(chatId)) {
            registerNewChat(chatId);
        }
        return chatRepository.findById(chatId).get().getContextType();
    }

    public Chat getChatById(long chatId) {
        return chatRepository.findById(chatId).get();
    }

    public void updateChat(Chat chat) {
        chatRepository.save(chat);
    }

    public List<Chat> getAllFreeHelpers() {
        return chatRepository.findAllByContextTypeAndReadyToHelp(ContextType.PASSIVE, true);
    }
}
