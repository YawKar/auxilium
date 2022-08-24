package dev.yawkar.auxilium.context;

import dev.yawkar.auxilium.bot.AuxiliumBot;
import dev.yawkar.auxilium.repository.entity.Chat;
import dev.yawkar.auxilium.service.ChatService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@PropertySource("classpath:contexts/newChatContext.properties")
public class NewChatContext extends AbstractContext {

    private final AuxiliumBot bot;
    private final ChatService chatService;
    @Value("${newChatContext.greeting}")
    private String greetingMessage;

    public NewChatContext(@Lazy AuxiliumBot bot, ChatService chatService) {
        this.bot = bot;
        this.chatService = chatService;
    }

    @SneakyThrows
    @Override
    public void handle(Update update) {
        bot.execute(new SendMessage(update.getMessage().getChatId().toString(), greetingMessage));
        Chat chat = chatService.getChatById(update.getMessage().getChatId());
        chat.setContextType(ContextType.PASSIVE);
        chatService.updateChat(chat);
        // here I do a trick with rerouting Update back to the bot
        // now it will be handled by PASSIVE context handler
        bot.onUpdateReceived(update);
    }
}
