package com.danthy.pizzafun.app.controllers.home;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.events.mediator.StartGameEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    public void initComponents() {
        pizzaFormNameRoot.setVisible(false);
    }

    @Override
    public void initListeners() {
        pizzaBgContainer.widthProperty().addListener((observable, oldValue, newValue) -> {
            pizzaBackground.setFitWidth(newValue.doubleValue());
        });

        pizzaBgContainer.heightProperty().addListener((observable, oldValue, newValue) -> {
            pizzaBackground.setFitHeight(newValue.doubleValue());
        });
    }

    @Override
    public void initActionEvents() {
        startGameButton.setOnMouseClicked(this::onStartGameEvent);
        playGameButton.setOnMouseClicked(this::onPlayGameEvent);
    }

    private void onPlayGameEvent(MouseEvent event) {
        pizzaFormNameRoot.setVisible(!pizzaFormNameRoot.isVisible());
    }

    private void onStartGameEvent(MouseEvent event) {
        String pizzaName = pizzaNameField.getText();

        sendEvent(new StartGameEvent(pizzaName));
    }
}