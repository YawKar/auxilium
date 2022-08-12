package dev.yawkar.auxilium.handler.command;

import dev.yawkar.auxilium.bot.AuxiliumBot;
import dev.yawkar.auxilium.handler.AbstractUpdateHandler;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class HelpHandler extends AbstractUpdateHandler {

    public HelpHandler(@Lazy AuxiliumBot auxiliumBot) {
        super(auxiliumBot);
    }

    @Override
    public void handle(Update update) {
        try {
            auxiliumBot.execute(new SendMessage(update.getMessage().getChatId().toString(), "help stub"));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isHandling(Update update) {
        return update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals("/help");
    }
}
