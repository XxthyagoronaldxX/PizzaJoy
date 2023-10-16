package com.danthy.pizzafun.app.contracts;

import com.danthy.pizzafun.app.logic.EventPublisher;
import javafx.fxml.Initializable;

public abstract class Controller extends Emitter implements Initializable, IListener {
    protected Controller(EventPublisher eventPublisher) {
        super(eventPublisher);
    }

    @Override
    public void update(IEvent event) {}
}
