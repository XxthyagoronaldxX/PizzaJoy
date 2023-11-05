package com.danthy.pizzafun.app.controllers.pizzaria.widgets.upgradecell;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.events.mediator.RequestLevelUpEvent;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class UpgradeCellListController implements IController, IMediatorEmitter {
    @FXML
    public Label upgradeTitleLabel;

    @FXML
    public Label upgradePriceLabel;

    @FXML
    public VBox upgradeButton;

    @FXML
    public ImageView cellBackgroundImg;

    @FXML
    public AnchorPane cellRoot;

    @FXML
    public ListView propertyListView;

    public UpgradeModel upgradeModel;

    @Override
    public void initComponents() {
        cellBackgroundImg.fitHeightProperty().bind(cellRoot.heightProperty());
        cellBackgroundImg.fitWidthProperty().bind(cellRoot.widthProperty());
    }

    public void setUpgradeModel(UpgradeModel upgradeModel) {
        this.upgradeModel = upgradeModel;

        upgradeTitleLabel.setText(upgradeModel.getName() + " Lv." + upgradeModel.getLevel());
        upgradePriceLabel.setText(String.format("$%.2f", upgradeModel.getUpgradeCost()));
        upgradeButton.setOnMouseClicked(this::onLevelUpEvent);
        propertyListView.setItems(FXCollections.observableList(upgradeModel.getLevelUpProperties()));
    }

    public void onLevelUpEvent(MouseEvent event) {
        this.sendEvent(new RequestLevelUpEvent(upgradeModel));
    }
}
