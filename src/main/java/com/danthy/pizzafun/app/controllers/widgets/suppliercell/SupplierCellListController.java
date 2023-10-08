package com.danthy.pizzafun.app.controllers.widgets.suppliercell;

import com.danthy.pizzafun.app.events.SetSupplierEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SupplierCellListController extends AnchorPane {
    @FXML
    private AnchorPane cellRoot;

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

    private final SupplierWrapper supplierWrapper;

    public SupplierCellListController(SupplierWrapper supplierWrapper) {
        this.supplierWrapper = supplierWrapper;

        FxmlUtil.loadFXMLInjectController(this, PathFxmlUtil.SUPPLIER_CELL_LIST_WIDGET);
        initialize();
    }

    private void initialize() {
        SupplierModel supplierModel = supplierWrapper.getWrapped();

        String name = supplierModel.getName();
        String cost = "Custo: R$" + supplierModel.getCost();
        String bonusChance = "Chance de Bonus: " + supplierModel.getBonusChance() + "%";
        String deliveryTimeInSeconds = "Tempo: " + supplierModel.getDeliveryTimeInSeconds() + "s";
        String buyToken = supplierModel.getBuyToken()  +" TK";

        costLabel.setText(cost);
        restockTimeLabel.setText(deliveryTimeInSeconds);
        nameLabel.setText(name);
        bonusChanceLabel.setText(bonusChance);
        buyTokenLabel.setText(buyToken);

        getChildren().add(cellRoot);
        setBottomAnchor(cellRoot, 0.0);
        setTopAnchor(cellRoot, 0.0);
    }

    @FXML
    public void setSupplierEvent(Event event) {
        GetIt.getInstance().find(EventPublisher.class).notifyAll(new SetSupplierEvent(supplierWrapper));
    }
}
