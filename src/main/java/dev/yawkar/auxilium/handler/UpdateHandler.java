package dev.yawkar.auxilium.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandler {

    void handle(Update update);

    boolean isHandling(Update update);
}
