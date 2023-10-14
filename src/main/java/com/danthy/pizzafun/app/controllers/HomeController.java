package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.app.states.StockState;
import com.danthy.pizzafun.app.states.TokenShopState;
import com.danthy.pizzafun.app.states.UpgradeState;
import com.danthy.pizzafun.domain.data.PizzaDataSingleton;
import com.danthy.pizzafun.domain.data.PostConstruct;
import com.danthy.pizzafun.domain.models.PizzaModel;
import com.danthy.pizzafun.domain.models.RoomModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController extends IController {
    @FXML
    public AnchorPane pizzaFormNameRoot;

    @FXML
    public TextField pizzaNameField;

    @FXML
    public Button startGameButton;

    @FXML
    public Button playGameButton;

    @FXML
    private ImageView pizzaBackground;

    @FXML
    private AnchorPane pizzaBgContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pizzaFormNameRoot.setVisible(false);

        pizzaBgContainer.widthProperty().addListener((observable, oldValue, newValue) -> {
            pizzaBackground.setFitWidth(newValue.doubleValue());
        });

        pizzaBgContainer.heightProperty().addListener((observable, oldValue, newValue) -> {
            pizzaBackground.setFitHeight(newValue.doubleValue());
        });

        startGameButton.setOnMouseClicked(this::onStartGameEvent);
        playGameButton.setOnMouseClicked(this::onPlayGameEvent);
    }

    public void onPlayGameEvent(MouseEvent event) {
        pizzaFormNameRoot.setVisible(!pizzaFormNameRoot.isVisible());
    }

    public void onStartGameEvent(MouseEvent event) {
        String pizzaName = pizzaNameField.getText();

        List<PizzaModel> pizzaModelList = new ArrayList<>(PizzaDataSingleton
                .getInstance()
                .getPizzaModels());
        RoomModel roomModel = PostConstruct.genRoomModel(pizzaName);

        GetIt.getInstance()
                .addSingleton(new StockState(roomModel))
                .addSingleton(new PizzariaState(roomModel))
                .addSingleton(new TokenShopState(roomModel, pizzaModelList))
                .addSingleton(new UpgradeState(roomModel))
                .addSingleton(roomModel);

        eventPublisher.notifyAll(new StartGameEvent());
    }
}