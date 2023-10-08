package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEmitter;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.app.states.StockState;
import com.danthy.pizzafun.app.wrappers.ItemStockWrapper;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;
import com.danthy.pizzafun.app.states.TokenShopState;
import com.danthy.pizzafun.domain.data.PostConstruct;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class HomeController extends IEmitter implements IController {
    @FXML
    public AnchorPane pizzaFormNameRoot;

    @FXML
    public TextField pizzaNameField;

    @FXML
    private ImageView pizzaBackground;

    @FXML
    private AnchorPane pizzaBgContainer;

    @FXML
    private void onPlayClick() {
        pizzaFormNameRoot.setVisible(true);
    }

    @FXML
    public void onInitClick() {
        String pizzaName = pizzaNameField.getText();

        RoomWrapper roomWrapper = new RoomWrapper(PostConstruct.genRoomModel(pizzaName));

        GetIt.getInstance()
                .addSingleton(new PizzariaState(roomWrapper));
        GetIt.getInstance()
                .addSingleton(new TokenShopState(new SupplierWrapper(PostConstruct.genSupplierModel())));
        GetIt.getInstance()
                .addSingleton(new StockState(roomWrapper));

        this.eventPublisher.notifyAll(new StartGameEvent());
    }

    @Override
    public void initialize() {
        pizzaFormNameRoot.setVisible(false);

        pizzaBgContainer.widthProperty().addListener((observable, oldValue, newValue) -> {
            pizzaBackground.setFitWidth(newValue.doubleValue());
        });

        pizzaBgContainer.heightProperty().addListener((observable, oldValue, newValue) -> {
            pizzaBackground.setFitHeight(newValue.doubleValue());
        });
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}