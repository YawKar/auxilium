package dev.yawkar.auxilium.context.dispatcher;

import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractDispatcher implements UpdateDispatcher {

    public abstract void dispatch(Update update);
}
