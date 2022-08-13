package dev.yawkar.auxilium.bot;

import dev.yawkar.auxilium.repository.ChatRepository;
import dev.yawkar.auxilium.repository.ChatStage;
import dev.yawkar.auxilium.repository.DialogRepository;
import dev.yawkar.auxilium.repository.entity.Chat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class AuxiliumBot extends TelegramLongPollingBot {

    private final ChatRepository chatRepository;
    private final DialogRepository dialogRepository;

    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;

    public AuxiliumBot(ChatRepository chatRepository, DialogRepository dialogRepository) {
        this.chatRepository = chatRepository;
        this.dialogRepository = dialogRepository;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            var message = update.getMessage();
            if (!chatRepository.existsById(message.getChatId())) {
                chatRepository.save(new Chat().setId(message.getChatId()).setStage(ChatStage.PASSIVE));
            }
            handleMessage(message);
        }
    }

    private void handleMessage(Message message) {
        Chat chat = chatRepository.findById(message.getChatId()).get();
        switch (chat.getStage()) {
            case ACTIVE -> activeMessage(chat, message);
            case PASSIVE -> passiveMessage(chat, message);
        }
    }

    private void passiveMessage(Chat chat, Message message) {
        if (message.hasText()) {
            String text = message.getText();
            switch (text) {
                case "/need_help" -> needHelp(chat);
                default -> sendHelpHint(chat);
            }
        }
    }

    private void sendHelpHint(Chat chat) {
        try {
            execute(SendMessage.builder()
                    .chatId(chat.getId())
                    .text("/need_help\n/end")
                    .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void needHelp(Chat chat) {
        chat.setStage(ChatStage.ACTIVE);
        chatRepository.save(chat);
        try {
            execute(SendMessage.builder()
                    .chatId(chat.getId())
                    .text("Start ACTIVE")
                    .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void activeMessage(Chat chat, Message message) {
        if (message.hasText()) {
            String text = message.getText();
            switch (text) {
                case "/end" -> endHelp(chat);
                default -> sendMessageToDialog(chat, text);
            }
        }
    }

    private void sendMessageToDialog(Chat chat, String text) {
        try {
            execute(SendMessage.builder()
                    .chatId(chat.getId())
                    .text(text)
                    .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void endHelp(Chat chat) {
        chat.setStage(ChatStage.PASSIVE);
        chatRepository.save(chat);
        try {
            execute(SendMessage.builder()
                    .chatId(chat.getId())
                    .text("Session ended")
                    .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
