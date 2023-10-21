package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.contracts.ReactOn;
import com.danthy.pizzafun.app.controllers.widgets.upgradecell.UpgradeCellListFactory;
import com.danthy.pizzafun.app.events.mediator.RequestLevelUpEvent;
import com.danthy.pizzafun.app.events.mediator.StartGameEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IUpgradeService;
import com.danthy.pizzafun.app.services.implementations.UpgradeServiceImpl;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UpgradeController implements IController, IMediatorEmitter {
    @FXML
    public ListView upgradeList;

    @FXML
    public HBox rootView;

    @FXML
    public FlowPane closeButton;

    @FXML
    public Label tokenCostLabel;

    @FXML
    public Label costLabel;

    private IUpgradeService upgradeService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        upgradeList.setCellFactory(object -> new UpgradeCellListFactory());
        upgradeList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @ReactOn(StartGameEvent.class)
    public void reactOnStartGameEvent(IEvent event) {
        upgradeService = GetIt.getInstance().find(UpgradeServiceImpl.class);

        upgradeService.getUpgradePizzariaObservableValue().getProperty().addListener((observable, oldValue, newValue) -> {
            tokenCostLabel.setText("Tokens: %d TK".formatted(newValue.getTokenUpgradeCost()));
            costLabel.setText("Dinheiro: $%f".formatted(newValue.getUpgradeCost()));
        });

        upgradeList.setItems(upgradeService.getUpgradeModelObservableList());
    }

    @FXML
    public void onLevelUpPizzaria(MouseEvent event) {
        this.sendEvent(new RequestLevelUpEvent(upgradeService.getUpgradePizzariaObservableValue().getValue()));
    }
}
