package com.danthy.pizzafun.app.contracts;

import com.danthy.pizzafun.app.logic.EventPublisher;

public abstract class Emitter {
    protected final EventPublisher eventPublisher;

    protected Emitter(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
