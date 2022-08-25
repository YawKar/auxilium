package dev.yawkar.auxilium.context;

import dev.yawkar.auxilium.bot.AuxiliumBot;
import dev.yawkar.auxilium.service.ParserUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
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

    private boolean isValidCommand(String command) {
        // TODO: rewrite command validation, make united storage for commands (both for validation and execution)
        return switch (command) {
            case "need_help", "open", "close" -> true;
            default -> false;
        };
    }

    @SneakyThrows
    @Override
    public void handle(Update update) {
        // TODO: rewrite it all with exceptions because it looks so stupid
        Message message = update.getMessage();
        if (message.hasText()) {
            String text = message.getText();
            if (ParserUtils.isCommand(text)) {
                String command = ParserUtils.parseCommand(text);
                if (isValidCommand(command)) {
                    switch (command) {
                        case "need_help" -> runNeedHelp(update);
                        case "open" -> runOpen(update);
                        case "close" -> runClose(update);
                    }
                } else {
                    bot.execute(new SendMessage(update.getMessage().getChatId().toString(), availableCommandsResponse));
                }
            } else {
                bot.execute(new SendMessage(update.getMessage().getChatId().toString(), availableCommandsResponse));
            }
        } else {
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
