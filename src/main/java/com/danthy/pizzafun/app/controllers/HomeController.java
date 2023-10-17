package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.events.mediator.StartGameEvent;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;
import com.danthy.pizzafun.app.logic.GetIt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements IController, IMediatorEmitter {
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

        sendEvent(new StartGameEvent(pizzaName));
    }

    @Override
    public void sendEvent(IEvent event) {
        GetIt.getInstance().find(ActionsMediator.class).notify(event);
    }
}