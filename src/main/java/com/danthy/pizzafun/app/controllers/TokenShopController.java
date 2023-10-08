package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEmitter;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.suppliercell.SupplierCellListFactory;
import com.danthy.pizzafun.app.events.ReStockEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.events.SupplierGenerateEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.wrappers.implementations.RoomWrapper;
import com.danthy.pizzafun.app.wrappers.implementations.SupplierWrapper;
import com.danthy.pizzafun.app.wrappers.implementations.TokenShopWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class TokenShopController extends IEmitter implements IController, IListener {
    @FXML
    public ListView supplierList;

    @FXML
    public AnchorPane upgradeViewContainer;

    @FXML
    public Label supplierNameLabel;

    @FXML
    public Label supplierRestockTimeLabel;

    @FXML
    public Label supplierBonusChanceLabel;

    @FXML
    public Label supplierCostLabel;

    @FXML
    public AnchorPane tokenShopViewSubRoot;

    @FXML
    public HBox tokenShopViewRoot;

    private RoomWrapper roomWrapper;

    private TokenShopWrapper tokenShopWrapper;

    public TokenShopController() {
    }

    @Override
    public void initialize() {
        initSupplierListView();
    }

    private void initSupplierListView() {
        supplierList.setCellFactory(object -> new SupplierCellListFactory());
        supplierList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void refreshCurrentSupplier() {
        SupplierWrapper supplierWrapper = roomWrapper.getSupplierWrapper();
        supplierNameLabel.setText(supplierWrapper.getNamePrint());
        supplierCostLabel.setText(supplierWrapper.getCostPrint());
        supplierBonusChanceLabel.setText(supplierWrapper.getBonusChancePrint());
        supplierRestockTimeLabel.setText(supplierWrapper.getDeliveryTimeInSecondsPrint());
    }

    @FXML
    public void closeTokenShopViewEvent(MouseEvent mouseEvent) {
        tokenShopViewRoot.toBack();
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        super.eventPublisher = eventPublisher;
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            roomWrapper = GetIt.getInstance().find(RoomWrapper.class);
            tokenShopWrapper = GetIt.getInstance().find(TokenShopWrapper.class);

            supplierList.setItems(tokenShopWrapper.getSupplierModelObservableList());
            refreshCurrentSupplier();
        } else if (event.getClass() == ReStockEvent.class) {
            refreshCurrentSupplier();
        } else if (event.getClass() == SupplierGenerateEvent.class) {
            SupplierGenerateEvent supplierGenerateEvent = (SupplierGenerateEvent) event;

            tokenShopWrapper.addAllSupplierModel(supplierGenerateEvent.supplierModelList());
        }
    }
}
