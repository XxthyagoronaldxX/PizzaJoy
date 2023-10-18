package com.danthy.pizzafun.app.contracts;

import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;

public interface IObserverEmitter {
    EventPublisher eventPublisher = GetIt.getInstance().find(EventPublisher.class);
}
