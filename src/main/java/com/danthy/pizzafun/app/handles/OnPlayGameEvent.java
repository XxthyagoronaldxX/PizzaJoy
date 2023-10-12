package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.controllers.HomeController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class OnPlayGameEvent implements EventHandler<MouseEvent> {
    private final AnchorPane pizzaFormNameRoot;

    public OnPlayGameEvent(HomeController controller) {
        pizzaFormNameRoot = controller.pizzaFormNameRoot;
    }

    @Override
    public void handle(MouseEvent event) {
        pizzaFormNameRoot.setVisible(!pizzaFormNameRoot.isVisible());
    }
}
