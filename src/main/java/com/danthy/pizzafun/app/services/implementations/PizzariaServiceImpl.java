package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.contracts.*;
import com.danthy.pizzafun.app.enums.NotifyType;
import com.danthy.pizzafun.app.events.mediator.*;
import com.danthy.pizzafun.app.events.services.SuccessBuySupplierEvent;
import com.danthy.pizzafun.app.events.services.SuccessLearnRecipeEvent;
import com.danthy.pizzafun.app.events.services.SuccessLevelUpEvent;
import com.danthy.pizzafun.app.events.services.ValidLevelUpEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderWrapper;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.domain.enums.UpgradeType;
import com.danthy.pizzafun.domain.models.PizzaModel;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;

public class PizzariaServiceImpl implements IPizzariaService, IMediatorEmitter, IObserverEmitter {
    private PizzariaState pizzariaState;

    @Override
    public ObservableValue<Double> getBalanceObservable() {
        return pizzariaState.getBalanceObservable();
    }

    @Override
    public ObservableValue<Integer> getTokensObservable() {
        return pizzariaState.getTokensObservable();
    }

    @Override
    public ObservableList<OrderWrapper> getOrderModelObservableList() {
        return pizzariaState.getOrderWrapperObservableList();
    }

    @Override
    public ObservableList<PizzaModel> getOwnedPizzaModelObservableList() {
        return pizzariaState.getOwnedPizzaModelObservableList();
    }

    @EventMap(SuccessBuySupplierEvent.class)
    private void onSuccessBuySupplierEvent(IEvent event) {
        SuccessBuySupplierEvent successBuySupplierEvent = (SuccessBuySupplierEvent) event;

        int buyToken = successBuySupplierEvent.supplierModel().getBuyToken();

        pizzariaState.decTokens(buyToken);
    }

    @EventMap(SuccessLearnRecipeEvent.class)
    private void onLearnRecipeEvent(IEvent event) {
        SuccessLearnRecipeEvent successLearnRecipeEvent = (SuccessLearnRecipeEvent) event;

        pizzariaState.decTokens((int) successLearnRecipeEvent.recipeWrapper().getPizzaModel().getPriceToBuyRecipe());
    }

    @EventMap(SuccessLevelUpEvent.class)
    private void onSuccessLevelUpEvent(IEvent event) {
        SuccessLevelUpEvent successLevelUpEvent = (SuccessLevelUpEvent) event;
        UpgradeModel upgradeModel = successLevelUpEvent.upgradeModel();

        if (upgradeModel.getUpgradeType() == UpgradeType.PIZZARIA)
            pizzariaState.incTotalOrderAmount(1);
        pizzariaState.decTokens(upgradeModel.getTokenUpgradeCost());
        pizzariaState.decBalance(upgradeModel.getUpgradeCost());
    }

    @ReactOn(StartGameEvent.class)
    public void reactOnStartGameEvent(IEvent event) {
        this.pizzariaState = GetIt.getInstance().find(PizzariaState.class);
    }

    @ReactOn(RequestLevelUpEvent.class)
    public void reactOnRequestLevelUpEvent(IEvent event) {
        RequestLevelUpEvent requestLevelUpEvent = (RequestLevelUpEvent) event;

        if (pizzariaState.getBalanceObservable().getValue() >= requestLevelUpEvent.upgradeModel().getUpgradeCost()) {
            eventPublisher.notifyAll(new ValidLevelUpEvent(requestLevelUpEvent.upgradeModel()));
        } else {
            this.sendEvent(new NotifyEvent(NotifyType.INSUFFICIENTMONEY));
        }
    }

    @ReactOn(RequestBuySupplierEvent.class)
    public void reactOnRequestBuySupplierEvent(IEvent event) {
        RequestBuySupplierEvent requestBuySupplierEvent = (RequestBuySupplierEvent) event;

        if (pizzariaState.getTokensObservable().getValue() >= requestBuySupplierEvent.supplierModel().getBuyToken()) {
            eventPublisher.notifyAll(new SuccessBuySupplierEvent(requestBuySupplierEvent.supplierModel()));
        } else {
            this.sendEvent(new NotifyEvent(NotifyType.INSUFFICIENTTOKEN));
        }
    }

    @ReactOn(RequestLearnRecipeEvent.class)
    public void reactOnRequestLearnRecipeEvent(IEvent event) {
        RequestLearnRecipeEvent requestLearnRecipeEvent = (RequestLearnRecipeEvent) event;

        float tokenToBuyRecipe = requestLearnRecipeEvent.recipeWrapper().getPizzaModel().getPriceToBuyRecipe();

        if (pizzariaState.getTokensObservable().getValue() >= tokenToBuyRecipe) {
            requestLearnRecipeEvent.controller().startToLearnTheRecipe();
            eventPublisher.notifyAll(new SuccessLearnRecipeEvent(requestLearnRecipeEvent.recipeWrapper()));
        } else {
            this.sendEvent(new NotifyEvent(NotifyType.INSUFFICIENTTOKEN));
        }
    }

    @ReactOn(SuccessProduceOrderEvent.class)
    public void reactOnSuccessProduceOrderEvent(IEvent event) {
        SuccessProduceOrderEvent successProduceOrderEvent = (SuccessProduceOrderEvent) event;

        OrderWrapper orderWrapper = successProduceOrderEvent.orderWrapper();

        pizzariaState.getOrderWrapperObservableList().remove(orderWrapper);
        pizzariaState.incBalance(orderWrapper.getOrderModel().getPizzaModel().getPriceToSell());
        pizzariaState.incTokens(1);
    }

    @ReactOn(OrderGenerateEvent.class)
    public void reactOnOrderGenerateEvent(IEvent event) {
        if (pizzariaState.getTotalOrderAmountObservable().getValue() > pizzariaState.getOrderWrapperObservableList().size()) {
            OrderGenerateEvent orderGenerateEvent = (OrderGenerateEvent) event;

            OrderWrapper orderWrapper = new OrderWrapper(orderGenerateEvent.orderModel());

            Platform.runLater(() -> {
                pizzariaState.getOrderWrapperObservableList().add(orderWrapper);
            });
        }
    }
}
