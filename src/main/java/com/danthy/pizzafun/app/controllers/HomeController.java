package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEmitter;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.app.states.StockState;
import com.danthy.pizzafun.app.states.TokenShopState;
import com.danthy.pizzafun.domain.data.PizzaDataSingleton;
import com.danthy.pizzafun.domain.data.PostConstruct;
import com.danthy.pizzafun.domain.models.PizzaModel;
import com.danthy.pizzafun.domain.models.RoomModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

        List<PizzaModel> pizzaModelList = PizzaDataSingleton.getInstance().getPizzaModels();
        RoomModel roomModel = PostConstruct.genRoomModel(pizzaName);

        GetIt.getInstance()
                .addSingleton(new StockState(roomModel))
                .addSingleton(new PizzariaState(roomModel))
                .addSingleton(new TokenShopState(roomModel, pizzaModelList));

        this.eventPublisher.notifyAll(new StartGameEvent());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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