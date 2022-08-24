package dev.yawkar.auxilium.context;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UserContext {

    void handle(Update update);
}
