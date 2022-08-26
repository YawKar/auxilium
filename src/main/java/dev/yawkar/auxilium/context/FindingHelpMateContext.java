package dev.yawkar.auxilium.context;

import dev.yawkar.auxilium.bot.AuxiliumBot;
import dev.yawkar.auxilium.exception.context.UnknownCommandException;
import dev.yawkar.auxilium.repository.entity.Chat;
import dev.yawkar.auxilium.service.ChatService;
import dev.yawkar.auxilium.service.FindingSessionQueueService;
import dev.yawkar.auxilium.service.ParserUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@PropertySource("classpath:contexts/findingHelpMateContext.properties")
@Component
public class FindingHelpMateContext extends AbstractContext {

    private final AuxiliumBot bot;
    private final ChatService chatService;
    private final FindingSessionQueueService findingSessionQueueService;
    @Value("${findingHelpMateContext.commands}")
    private String availableCommandsResponse;

    public FindingHelpMateContext(
            @Lazy AuxiliumBot bot,
            ChatService chatService,
            FindingSessionQueueService findingSessionQueueService) {
        this.bot = bot;
        this.chatService = chatService;
        this.findingSessionQueueService = findingSessionQueueService;
    }

    @SneakyThrows
    @Override
    public void handle(Update update) {
        try {
            switch (ParserUtils.parseCommand(update.getMessage().getText())) {
                case "stop_help" -> runStopHelp(update);
                default -> throw new UnknownCommandException();
            }
        } catch (RuntimeException e) {
            bot.execute(new SendMessage(update.getMessage().getChatId().toString(), availableCommandsResponse));
        }
    }

    @SneakyThrows
    private void runStopHelp(Update update) {
        Chat chat = chatService.getChatById(update.getMessage().getChatId());
        chat.setContextType(ContextType.PASSIVE);
        chatService.updateChat(chat);
        findingSessionQueueService.removeRequesterChatId(chat.getId());
        bot.execute(new SendMessage(update.getMessage().getChatId().toString(), "Stopped finding process"));
    }
}
