package dev.yawkar.auxilium.dispatcher;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateDispatcher {

    void dispatch(Update update);
}
