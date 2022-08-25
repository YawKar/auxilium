package dev.yawkar.auxilium.exception.context;

import dev.yawkar.auxilium.exception.AuxiliumBaseException;

public class UnknownCommandException extends AuxiliumBaseException {

    public UnknownCommandException() {
        super();
    }

    public UnknownCommandException(String message) {
        super(message);
    }
}
