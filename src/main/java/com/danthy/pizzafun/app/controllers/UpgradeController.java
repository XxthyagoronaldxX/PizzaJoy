package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.controllers.widgets.upgradecell.UpgradeCellListFactory;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IUpgradeService;
import com.danthy.pizzafun.app.services.implementations.IUpgradeServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UpgradeController implements IController {
    @FXML
    public ListView upgradeList;

    @FXML
    public HBox rootView;

    @FXML
    public FlowPane closeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        upgradeList.setCellFactory(object -> new UpgradeCellListFactory());
        upgradeList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void reactOnStartGameEvent(IEvent event) {
        IUpgradeService upgradeService = GetIt.getInstance().find(IUpgradeServiceImpl.class);

        upgradeList.setItems(upgradeService.getUpgradeModelObservableList());
    }
}
