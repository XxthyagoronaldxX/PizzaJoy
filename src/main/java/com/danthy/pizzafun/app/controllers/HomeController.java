package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.handles.OnPlayGameEvent;
import com.danthy.pizzafun.app.handles.OnStartGameEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
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

        startGameButton.setOnMouseClicked(new OnStartGameEvent(this));
        playGameButton.setOnMouseClicked(new OnPlayGameEvent(this));
    }
}