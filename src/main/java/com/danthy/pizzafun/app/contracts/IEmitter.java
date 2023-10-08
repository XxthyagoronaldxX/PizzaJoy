package com.danthy.pizzafun.app.contracts;

import com.danthy.pizzafun.app.logic.EventPublisher;

public abstract class IEmitter {
    protected EventPublisher eventPublisher;

    public abstract void setEventPublisher(EventPublisher eventPublisher);
}
