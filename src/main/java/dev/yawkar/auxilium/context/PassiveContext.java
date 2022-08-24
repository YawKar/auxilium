package dev.yawkar.auxilium.context;

import dev.yawkar.auxilium.bot.AuxiliumBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class PassiveContext extends AbstractContext {

    private final AuxiliumBot bot;

    public PassiveContext(AuxiliumBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update) {

    }
}
