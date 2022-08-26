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

@PropertySource("classpath:contexts/findingHelpMateContext.properties")
@Component
public class FindingHelpMateContext extends AbstractContext {

    private final AuxiliumBot bot;
    @Value("${findingHelpMateContext.commands}")
    private String availableCommandsResponse;

    public FindingHelpMateContext(@Lazy AuxiliumBot bot) {
        this.bot = bot;
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
        bot.execute(new SendMessage(update.getMessage().getChatId().toString(), "stop_help"));
    }
}
