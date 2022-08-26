package dev.yawkar.auxilium.context;

import dev.yawkar.auxilium.bot.AuxiliumBot;
import dev.yawkar.auxilium.exception.context.UnknownCommandException;
import dev.yawkar.auxilium.repository.entity.Chat;
import dev.yawkar.auxilium.repository.entity.Session;
import dev.yawkar.auxilium.service.ChatService;
import dev.yawkar.auxilium.service.ParserUtils;
import dev.yawkar.auxilium.service.SessionService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@PropertySource("classpath:contexts/activeContext.properties")
@Component
public class ActiveContext extends AbstractContext {

    private final AuxiliumBot bot;
    private final ChatService chatService;
    private final SessionService sessionService;
    @Value("${activeContext.commands}")
    private String availableCommandsResponse;

    public ActiveContext(@Lazy AuxiliumBot bot, ChatService chatService, SessionService sessionService) {
        this.bot = bot;
        this.chatService = chatService;
        this.sessionService = sessionService;
    }

    @SneakyThrows
    @Override
    public void handle(Update update) {
        try {
            switch (ParserUtils.parseCommand(update.getMessage().getText())) {
                case "end_session" -> runEndSession(update);
                default -> throw new UnknownCommandException();
            }
        } catch (RuntimeException e) {
            if (update.getMessage().hasText() && !ParserUtils.isCommand(update.getMessage().getText())) {
                exchangeMessage(update);
            } else {
                bot.execute(new SendMessage(update.getMessage().getChatId().toString(), availableCommandsResponse));
            }
        }
    }

    @SneakyThrows
    private void exchangeMessage(Update update) {
        Chat chat = chatService.getChatById(update.getMessage().getChatId());
        Session session = sessionService.getSessionById(chat.getSessionId());
        long interlocutorId = chat.getId() == session.getHelperId() ? session.getRequesterId() : session.getHelperId();
        bot.execute(new SendMessage(Long.toString(interlocutorId), update.getMessage().getText()));
    }

    @SneakyThrows
    private void runEndSession(Update update) {
        Chat chat = chatService.getChatById(update.getMessage().getChatId());
        chat.setContextType(ContextType.PASSIVE);
        chatService.updateChat(chat);
        bot.execute(new SendMessage(update.getMessage().getChatId().toString(), availableCommandsResponse));
    }
}
