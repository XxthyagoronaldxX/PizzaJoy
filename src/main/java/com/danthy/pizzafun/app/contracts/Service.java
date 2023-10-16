package com.danthy.pizzafun.app.contracts;

import com.danthy.pizzafun.app.logic.EventPublisher;

public abstract class Service extends Emitter implements IListener {
    protected Service(EventPublisher eventPublisher) {
        super(eventPublisher);
    }
}
