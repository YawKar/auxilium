package dev.yawkar.auxilium.context;

import dev.yawkar.auxilium.bot.AuxiliumBot;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class NewChatContext extends AbstractContext {

    private final AuxiliumBot bot;

    public NewChatContext(@Lazy AuxiliumBot bot) {
        this.bot = bot;
    }

    @SneakyThrows
    @Override
    public void handle(Update update) {
        bot.execute(new SendMessage(update.getMessage().getChatId().toString(), "Hello!"));
    }
}
