package dev.yawkar.auxilium;

import dev.yawkar.auxilium.bot.AuxiliumBot;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.BotSession;

@Component
public class Starter implements CommandLineRunner {

    private final TelegramBotsApi telegramBotsApi;
    private final AuxiliumBot auxiliumBot;

    public Starter(TelegramBotsApi telegramBotsApi, AuxiliumBot auxiliumBot) {
        this.telegramBotsApi = telegramBotsApi;
        this.auxiliumBot = auxiliumBot;
    }

    @Override
    public void run(String... args) throws Exception {
        BotSession botSession = telegramBotsApi.registerBot(auxiliumBot);
    }
}
