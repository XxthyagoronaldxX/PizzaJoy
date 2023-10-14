package com.danthy.pizzafun.app.controllers.widgets.upgradecell;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.events.RequestLevelUpEvent;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UpgradeCellListController extends IController {
    @FXML
    public Label upgradeTitleLabel;

    @FXML
    public Label upgradePriceLabel;

    @FXML
    public VBox upgradeButton;

    public UpgradeModel upgradeModel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUpgradeModel(UpgradeModel upgradeModel) {
        this.upgradeModel = upgradeModel;

        upgradeTitleLabel.setText(upgradeModel.getName() + " Lv." + upgradeModel.getLevel());
        upgradePriceLabel.setText(String.format("$%.2f", upgradeModel.getUpgradeCost()));
        upgradeButton.setOnMouseClicked(this::onLevelUpEvent);
    }

    public void onLevelUpEvent(MouseEvent event) {
        eventPublisher.notifyAll(new RequestLevelUpEvent(upgradeModel));
    }
}
