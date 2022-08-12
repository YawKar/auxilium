package dev.yawkar.auxilium.dispatcher;

import dev.yawkar.auxilium.handler.UpdateHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class BasicDispatcher implements UpdateDispatcher {

    List<UpdateHandler> handlers;

    public BasicDispatcher(List<UpdateHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void dispatch(Update update) {
        for (var handler : handlers) {
            if (handler.isHandling(update)) {
                handler.handle(update);
                break;
            }
        }
    }
}
