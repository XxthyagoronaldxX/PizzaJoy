package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.contracts.Emitter;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.enums.NotifyType;
import com.danthy.pizzafun.app.events.*;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderWrapper;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.domain.models.PizzaModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;

public class PizzariaServiceImpl extends Emitter implements IPizzariaService, IMediatorEmitter {
    private PizzariaState pizzariaState;

    public PizzariaServiceImpl(EventPublisher eventPublisher) {
        super(eventPublisher);
    }

    @Override
    public int getTokens() {
        return pizzariaState.getTokensObservable().getValue();
    }

    @Override
    public void addOrder(OrderWrapper orderWrapper) {
        Platform.runLater(() -> {
            pizzariaState.getOrderModelObservableList().add(orderWrapper);
        });
    }

    @Override
    public void removeOrder(OrderWrapper orderWrapper) {
        Platform.runLater(() -> {
            pizzariaState.getOrderModelObservableList().remove(orderWrapper);

            pizzariaState.incBalance(orderWrapper.getOrderModel().getPizzaModel().getPriceToSell());
            pizzariaState.incTokens(1);
        });
    }

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
        return pizzariaState.getOrderModelObservableList();
    }

    @Override
    public ObservableList<PizzaModel> getOwnedPizzaModelObservableList() {
        return pizzariaState.getOwnedPizzaModelObservableList();
    }

    @Override
    public int getOrdersAmount() {
        return pizzariaState.getOrderModelObservableList().size();
    }

    public void reactOnStartGameEvent(IEvent event) {
        this.pizzariaState = GetIt.getInstance().find(PizzariaState.class);
    }

    public void reactOnRequestLevelUpEvent(IEvent event) {
        RequestLevelUpEvent requestLevelUpEvent = (RequestLevelUpEvent) event;

        if (pizzariaState.getBalanceObservable().getValue() >= requestLevelUpEvent.upgradeModel().getUpgradeCost()) {
            eventPublisher.notifyAll(new SuccessLevelUpEvent(requestLevelUpEvent.upgradeModel()));
        } else {
            this.sendEvent(new NotifyEvent(NotifyType.INSUFFICIENTMONEY));
        }
    }

    public void reactOnRequestBuySupplierEvent(IEvent event) {
        RequestBuySupplierEvent requestBuySupplierEvent = (RequestBuySupplierEvent) event;

        if (pizzariaState.getTokensObservable().getValue() >= requestBuySupplierEvent.supplierModel().getBuyToken()) {
            eventPublisher.notifyAll(new SuccessBuySupplierEvent(requestBuySupplierEvent.supplierModel()));
        } else {
            this.sendEvent(new NotifyEvent(NotifyType.INSUFFICIENTTOKEN));
        }
    }

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

    public void reactOnSuccessProduceOrderEvent(IEvent event) {
        SuccessProduceOrderEvent successProduceOrderEvent = (SuccessProduceOrderEvent) event;

        removeOrder(successProduceOrderEvent.orderWrapper());
    }

    public void reactOnOrderGenerateEvent(IEvent event) {
        OrderGenerateEvent orderGenerateEvent = (OrderGenerateEvent) event;

        addOrder(new OrderWrapper(orderGenerateEvent.orderWrapper()));
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == SuccessBuySupplierEvent.class) onSuccessBuySupplierEvent(event);

        else if (event.getClass() == SuccessLearnRecipeEvent.class) onLearnRecipeEvent(event);

        else if (event.getClass() == SuccessLevelUpEvent.class) onSuccessLevelUpEvent(event);
    }

    private void onSuccessBuySupplierEvent(IEvent event) {
        SuccessBuySupplierEvent successBuySupplierEvent = (SuccessBuySupplierEvent) event;

        int buyToken = successBuySupplierEvent.supplierModel().getBuyToken();

        pizzariaState.decTokens(buyToken);
    }

    private void onLearnRecipeEvent(IEvent event) {
        SuccessLearnRecipeEvent successLearnRecipeEvent = (SuccessLearnRecipeEvent) event;

        pizzariaState.decTokens((int) successLearnRecipeEvent.recipeWrapper().getPizzaModel().getPriceToBuyRecipe());
    }

    private void onSuccessLevelUpEvent(IEvent event) {
        SuccessLevelUpEvent successLevelUpEvent = (SuccessLevelUpEvent) event;

        pizzariaState.decBalance(successLevelUpEvent.upgradeModel().getUpgradeCost());
    }

    @Override
    public void sendEvent(IEvent event) {
        GetIt.getInstance().find(ActionsMediator.class).notify(event);
    }
}
