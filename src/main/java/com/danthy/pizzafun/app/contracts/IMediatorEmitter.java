package com.danthy.pizzafun.app.contracts;

import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;

public interface IMediatorEmitter {
    default void sendEvent(IEvent event) {
        GetIt.getInstance().find(ActionsMediator.class).notify(event);
    }
}
