package dev.yawkar.auxilium.context;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class PassiveContext extends AbstractContext {

    @Override
    public void handle(Update update) {

    }
}
