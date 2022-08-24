package dev.yawkar.auxilium.context;

import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractContext implements UserContext {

    public abstract void handle(Update update);
}
