package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.controllers.HomeController;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.app.states.StockState;
import com.danthy.pizzafun.app.states.TokenShopState;
import com.danthy.pizzafun.app.states.UpgradeState;
import com.danthy.pizzafun.domain.data.PizzaDataSingleton;
import com.danthy.pizzafun.domain.data.PostConstruct;
import com.danthy.pizzafun.domain.models.PizzaModel;
import com.danthy.pizzafun.domain.models.RoomModel;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OnStartGameEvent implements EventHandler<MouseEvent> {
    private final TextField pizzaNameLabel;

    public OnStartGameEvent(HomeController homeController) {
        pizzaNameLabel = homeController.pizzaNameField;
    }

    @Override
    public void handle(MouseEvent event) {

    }
}
