package com.danthy.pizzafun.app.controllers.pizzaria.widgets.suppliercell;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.events.mediator.RequestBuySupplierEvent;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SupplierCellListController implements IController, IMediatorEmitter {
    @FXML
    private Label bonusChanceLabel;

    @FXML
    private Label costLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label restockTimeLabel;

    @FXML
    private Label buyTokenLabel;

    private SupplierModel supplierModel;

    @FXML
    public void onSetSupplierEvent(Event event) {
        sendEvent(new RequestBuySupplierEvent(supplierModel));
    }

    public void setSupplierModel(SupplierModel supplierModel) {
        this.supplierModel = supplierModel;

        String name = supplierModel.getName();
        String bonusChance = "Chance de Bonus: " + supplierModel.getBonusChance() + "%";
        String deliveryTimeInSeconds = "Tempo: " + supplierModel.getDeliveryTimeInSeconds() + "s";
        String buyToken = supplierModel.getBuyToken() + " TK";

        restockTimeLabel.setText(deliveryTimeInSeconds);
        nameLabel.setText(name);
        bonusChanceLabel.setText(bonusChance);
        buyTokenLabel.setText(buyToken);
    }
}
