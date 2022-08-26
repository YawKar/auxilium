package dev.yawkar.auxilium.context;

import dev.yawkar.auxilium.bot.AuxiliumBot;
import dev.yawkar.auxilium.exception.context.UnknownCommandException;
import dev.yawkar.auxilium.repository.entity.Chat;
import dev.yawkar.auxilium.service.ChatService;
import dev.yawkar.auxilium.service.ParserUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@PropertySource("classpath:contexts/passiveContext.properties")
public class PassiveContext extends AbstractContext {

    private final AuxiliumBot bot;
    private final ChatService chatService;
    @Value("${passiveContext.commands}")
    private String availableCommandsResponse;

    public PassiveContext(@Lazy AuxiliumBot bot, ChatService chatService) {
        this.bot = bot;
        this.chatService = chatService;
    }

    @SneakyThrows
    @Override
    public void handle(Update update) {
        try {
            switch (ParserUtils.parseCommand(update.getMessage().getText())) {
                case "need_help" -> runNeedHelp(update);
                case "open" -> runOpen(update);
                case "close" -> runClose(update);
                default -> throw new UnknownCommandException();
            }
        } catch (RuntimeException e) {
            bot.execute(new SendMessage(update.getMessage().getChatId().toString(), availableCommandsResponse));
        }
    }

    @SneakyThrows
    private void runClose(Update update) {
        Chat chat = chatService.getChatById(update.getMessage().getChatId());
        chat.setReadyToHelp(false);
        chatService.updateChat(chat);
        bot.execute(new SendMessage(update.getMessage().getChatId().toString(),
                "You've removed from the list of helpers"));
    }

    @SneakyThrows
    private void runOpen(Update update) {
        Chat chat = chatService.getChatById(update.getMessage().getChatId());
        chat.setReadyToHelp(true);
        chatService.updateChat(chat);
        bot.execute(new SendMessage(update.getMessage().getChatId().toString(),
                "You've registered as a helper! Thank you!"));
    }

    @SneakyThrows
    private void runNeedHelp(Update update) {
        Chat chat = chatService.getChatById(update.getMessage().getChatId());
        chat.setContextType(ContextType.FINDING_HELP_MATE);
        chatService.updateChat(chat);
        bot.execute(new SendMessage(update.getMessage().getChatId().toString(),
                "The finding helpmate process was started! You will be connected to the first freed helper!"));
    }
}
