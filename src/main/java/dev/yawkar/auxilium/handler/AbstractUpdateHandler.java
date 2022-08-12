package dev.yawkar.auxilium.handler;

import dev.yawkar.auxilium.bot.AuxiliumBot;

public abstract class AbstractUpdateHandler implements UpdateHandler {

    protected AuxiliumBot auxiliumBot;

    public AbstractUpdateHandler(AuxiliumBot auxiliumBot) {
        this.auxiliumBot = auxiliumBot;
    }
}
