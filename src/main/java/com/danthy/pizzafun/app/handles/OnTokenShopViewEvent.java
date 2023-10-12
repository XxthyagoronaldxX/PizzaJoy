package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.controllers.PizzariaController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class OnTokenShopViewEvent implements EventHandler<MouseEvent> {
    private final AnchorPane tokenShopWrapperPane;

    public OnTokenShopViewEvent(PizzariaController controller) {
        tokenShopWrapperPane = controller.tokenShopWrapperPane;
    }

    @Override
    public void handle(MouseEvent event) {
        tokenShopWrapperPane.setVisible(!tokenShopWrapperPane.isVisible());
    }
}
