package com.danthy.pizzafun.app.contracts;

import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;

public abstract class IEmitter {
    protected final EventPublisher eventPublisher;

    protected IEmitter() {
        eventPublisher = GetIt.getInstance().find(EventPublisher.class);
    }
}
