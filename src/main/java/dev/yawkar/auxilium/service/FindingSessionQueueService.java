package dev.yawkar.auxilium.service;

import dev.yawkar.auxilium.bot.AuxiliumBot;
import dev.yawkar.auxilium.context.ContextType;
import dev.yawkar.auxilium.repository.entity.Chat;
import dev.yawkar.auxilium.repository.entity.Session;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.*;

@Service
public class FindingSessionQueueService {

    private final Set<Long> chatIdsWaitForHelp = new HashSet<>();
    private final AuxiliumBot bot;
    private final ChatService chatService;
    private final SessionService sessionService;

    public FindingSessionQueueService(
            @Lazy AuxiliumBot bot,
            ChatService chatService,
            SessionService sessionService) {
        this.bot = bot;
        this.chatService = chatService;
        this.sessionService = sessionService;
    }

    public void addNewRequesterChatId(long requesterChatId) {
        chatIdsWaitForHelp.add(requesterChatId);
        tryToProcess();
    }

    public synchronized void tryToProcess() {
        if (chatIdsWaitForHelp.isEmpty())
            return;
        List<Chat> helpers = chatService.getAllFreeHelpers();
        Iterator<Long> iter = chatIdsWaitForHelp.iterator();
        int currentHelperIndex = 0;
        while (iter.hasNext() && currentHelperIndex < helpers.size()) {
            long requesterId = iter.next();
            Chat requesterChat = chatService.getChatById(requesterId);
            Chat helperChat = helpers.get(currentHelperIndex);
            Session newSession = sessionService.updateOrSaveSession(new Session()
                    .setHelperId(helperChat.getId())
                    .setRequesterId(requesterId));
            requesterChat.setSessionId(newSession.getId());
            helperChat.setSessionId(newSession.getId());
            requesterChat.setContextType(ContextType.ACTIVE);
            helperChat.setContextType(ContextType.ACTIVE);
            chatService.updateChat(requesterChat);
            chatService.updateChat(helperChat);
            sendNotificationAboutConnectingToSession(requesterChat);
            sendNotificationAboutConnectingToSession(helperChat);
            ++currentHelperIndex;
            iter.remove();
        }
    }

    @SneakyThrows
    private void sendNotificationAboutConnectingToSession(Chat chat) {
        bot.execute(new SendMessage(Long.toString(chat.getId()), "You are now connected to the session: %d!\nAll your messages will be redirected to the other person!".formatted(chat.getSessionId())));
    }

    public void removeRequesterChatId(long requesterId) {
        chatIdsWaitForHelp.remove(requesterId);
    }
}
