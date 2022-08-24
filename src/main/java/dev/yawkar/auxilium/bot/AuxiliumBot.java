package dev.yawkar.auxilium.bot;

import dev.yawkar.auxilium.context.dispatcher.UpdateDispatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AuxiliumBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;
    private final UpdateDispatcher dispatcher;

    public AuxiliumBot(@Qualifier("Alpha Dispatcher") UpdateDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            dispatcher.dispatch(update);
        }
    }
}
