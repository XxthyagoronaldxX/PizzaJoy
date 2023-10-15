package com.danthy.pizzafun.app.contracts;

import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import javafx.fxml.Initializable;

public abstract class IController extends IEmitter implements Initializable, IListener {
    @Override
    public void update(IEvent event) {}
}
