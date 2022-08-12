package dev.yawkar.auxilium.handler;

import dev.yawkar.auxilium.bot.AuxiliumBot;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class SimpleTextMessageHandler extends AbstractUpdateHandler {

    public SimpleTextMessageHandler(@Lazy AuxiliumBot auxiliumBot) {
        super(auxiliumBot);
    }

    @Override
    public void handle(Update update) {
        try {
            auxiliumBot.execute(new SendMessage(update.getMessage().getChatId().toString(), update.getMessage().getText()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isHandling(Update update) {
        return update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().charAt(0) != '/';
    }
}
