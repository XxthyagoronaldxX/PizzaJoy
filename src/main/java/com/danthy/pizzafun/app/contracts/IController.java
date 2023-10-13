package com.danthy.pizzafun.app.contracts;

import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import javafx.fxml.Initializable;

public abstract class IController implements Initializable {
    protected final EventPublisher eventPublisher;

    protected IController() {
        eventPublisher = GetIt.getInstance().find(EventPublisher.class);
    }
}
