package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEmitter;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.suppliercell.SupplierCellListFactory;
import com.danthy.pizzafun.app.services.ITokenShopService;
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
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TokenShopController extends IEmitter implements IController, IListener {
    @FXML
    public ListView supplierList;

    @FXML
    public Label supplierNameLabel;

    @FXML
    public Label supplierRestockTimeLabel;

    @FXML
    public Label supplierBonusChanceLabel;

    @FXML
    public Label supplierCostLabel;

    @FXML
    public AnchorPane rootView;

    @FXML
    public ListView pizzaRecipeList;

    private ITokenShopService tokenShopService;

    public TokenShopController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        supplierList.setCellFactory(object -> new SupplierCellListFactory());
        supplierList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        pizzaRecipeList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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
            pizzaRecipeList.setItems(tokenShopState.getPizzaWrapperObservableList());

            initObservers();
        }
    }
}
