package com.danthy.pizzafun.app.contracts;

import com.danthy.pizzafun.app.utils.EventPublisher;

public abstract class Controller {
    protected EventPublisher eventPublisher;

    protected abstract void initialize();

    public abstract void setEventPublisher(EventPublisher eventPublisher);
}
