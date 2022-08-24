package dev.yawkar.auxilium.context.dispatcher;

import dev.yawkar.auxilium.context.ContextType;
import dev.yawkar.auxilium.context.UserContext;
import dev.yawkar.auxilium.service.ChatService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("Alpha Dispatcher")
public class AlphaDispatcher extends AbstractDispatcher {

    private final ChatService chatService;
    private final UserContext passiveContext;
    private final UserContext findingContext;
    private final UserContext activeContext;

    public AlphaDispatcher(
            ChatService chatService,
            @Qualifier("passiveContext") UserContext passiveContext,
            @Qualifier("findingHelpMateContext") UserContext findingContext,
            @Qualifier("activeContext") UserContext activeContext
    ) {
        this.chatService = chatService;
        this.passiveContext = passiveContext;
        this.findingContext = findingContext;
        this.activeContext = activeContext;
    }

    @Override
    public void dispatch(Update update) {
        ContextType contextType = chatService.getContextType(update.getMessage().getChatId());
        switch (contextType) {
            case PASSIVE -> passiveContext.handle(update);
            case FINDING_HELP_MATE -> findingContext.handle(update);
            case ACTIVE -> activeContext.handle(update);
        }
    }
}
