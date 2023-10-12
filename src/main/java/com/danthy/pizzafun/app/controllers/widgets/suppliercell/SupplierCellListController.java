package com.danthy.pizzafun.app.controllers.widgets.suppliercell;

import com.danthy.pizzafun.app.events.SetSupplierEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SupplierCellListController implements Initializable {
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

    public SupplierCellListController() {
    }

    @FXML
    public void onSetSupplierEvent(Event event) {
        GetIt.getInstance().find(EventPublisher.class).notifyAll(new SetSupplierEvent(supplierModel));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setSupplierModel(SupplierModel supplierModel) {
        this.supplierModel = supplierModel;

        String name = supplierModel.getName();
        String cost = "Custo: R$" + supplierModel.getCost();
        String bonusChance = "Chance de Bonus: " + supplierModel.getBonusChance() + "%";
        String deliveryTimeInSeconds = "Tempo: " + supplierModel.getDeliveryTimeInSeconds() + "s";
        String buyToken = supplierModel.getBuyToken() + " TK";

        costLabel.setText(cost);
        restockTimeLabel.setText(deliveryTimeInSeconds);
        nameLabel.setText(name);
        bonusChanceLabel.setText(bonusChance);
        buyTokenLabel.setText(buyToken);
    }
}
