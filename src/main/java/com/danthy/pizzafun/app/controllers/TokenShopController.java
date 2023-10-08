package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEmitter;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.suppliercell.SupplierCellListFactory;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.services.ITokenShopService;
import com.danthy.pizzafun.app.events.ReStockEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.TokenShopServiceImpl;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;
import com.danthy.pizzafun.app.states.TokenShopState;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.beans.property.Property;
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

    private ITokenShopService tokenShopService;

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

    public void initObservers() {
        Property<SupplierWrapper> supplierWrapperProperty = tokenShopService.supplierWrapperProperty();

        supplierWrapperProperty.addListener((observer, oldValue, newValue) -> {
            refreshSupplier(newValue);
        });

        refreshSupplier(supplierWrapperProperty.getValue());
    }

    public void refreshSupplier(SupplierWrapper supplierWrapper) {
        SupplierModel supplierModel = supplierWrapper.getWrapped();

        String name = supplierModel.getName();
        String cost = "Custo: R$" + supplierModel.getCost();
        String bonusChance = "Chance de Bonus: " + supplierModel.getBonusChance() + "%";
        String deliveryTimeInSeconds = "Tempo: " + supplierModel.getDeliveryTimeInSeconds() + "s";

        supplierNameLabel.setText(name);
        supplierCostLabel.setText(cost);
        supplierBonusChanceLabel.setText(bonusChance);
        supplierRestockTimeLabel.setText(deliveryTimeInSeconds);
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
            tokenShopService = GetIt.getInstance().find(TokenShopServiceImpl.class);

            TokenShopState tokenShopState = tokenShopService.getTokenShopWrapper();

            supplierList.setItems(tokenShopState.getSupplierModelObservableList());
            initObservers();
        }
    }
}
