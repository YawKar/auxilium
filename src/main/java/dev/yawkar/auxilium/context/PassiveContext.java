package dev.yawkar.auxilium.context;

import dev.yawkar.auxilium.bot.AuxiliumBot;
import dev.yawkar.auxilium.exception.context.UnknownCommandException;
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
    @Value("${passiveContext.commands}")
    private String availableCommandsResponse;

    public PassiveContext(@Lazy AuxiliumBot bot) {
        this.bot = bot;
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
        // TODO: rewrite
        bot.execute(new SendMessage(update.getMessage().getChatId().toString(), "close command"));
    }

    @SneakyThrows
    private void runOpen(Update update) {
        // TODO: rewrite
        bot.execute(new SendMessage(update.getMessage().getChatId().toString(), "open command"));
    }

    @SneakyThrows
    private void runNeedHelp(Update update) {
        // TODO: rewrite
        bot.execute(new SendMessage(update.getMessage().getChatId().toString(), "need_help command"));
    }
}
