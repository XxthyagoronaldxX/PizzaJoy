package com.danthy.pizzafun.app.controllers.widgets.upgradecell;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.events.mediator.RequestLevelUpEvent;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UpgradeCellListController implements IController, IMediatorEmitter {
    @FXML
    public Label upgradeTitleLabel;

    @FXML
    public Label upgradePriceLabel;

    @FXML
    public VBox upgradeButton;

    public UpgradeModel upgradeModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void setUpgradeModel(UpgradeModel upgradeModel) {
        this.upgradeModel = upgradeModel;

        upgradeTitleLabel.setText(upgradeModel.getName() + " Lv." + upgradeModel.getLevel());
        upgradePriceLabel.setText(String.format("$%.2f", upgradeModel.getUpgradeCost()));
        upgradeButton.setOnMouseClicked(this::onLevelUpEvent);
    }

    public void onLevelUpEvent(MouseEvent event) {
        this.sendEvent(new RequestLevelUpEvent(upgradeModel));
    }
}
