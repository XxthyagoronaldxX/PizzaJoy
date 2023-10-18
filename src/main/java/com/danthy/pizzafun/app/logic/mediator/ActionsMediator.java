package com.danthy.pizzafun.app.logic.mediator;

import com.danthy.pizzafun.app.events.mediator.*;
import com.danthy.pizzafun.app.managers.GameManager;
import com.danthy.pizzafun.app.managers.ScreenManager;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediator;
import com.danthy.pizzafun.app.controllers.*;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
import com.danthy.pizzafun.app.services.implementations.TokenShopServiceImpl;
import com.danthy.pizzafun.app.services.implementations.UpgradeServiceImpl;
import com.danthy.pizzafun.app.fluxs.AutoSaveFlux;
import com.danthy.pizzafun.app.fluxs.GenItemStockFlux;
import com.danthy.pizzafun.app.fluxs.GenOrderFlux;
import com.danthy.pizzafun.app.fluxs.GenSupplierFlux;

public class ActionsMediator implements IMediator {
    private final ControllerFacade controllerFacade;
    private final ServiceFacade serviceFacade;
    private final ManagerFacade managerFacade;
    private final FluxFacade fluxFacade;

    public ActionsMediator(ControllerFacade controllerFacade, ServiceFacade serviceFacade, ManagerFacade managerFacade, FluxFacade fluxFacade) {
        this.controllerFacade = controllerFacade;
        this.serviceFacade = serviceFacade;
        this.managerFacade = managerFacade;
        this.fluxFacade = fluxFacade;
    }

    @Override
    public void notify(IEvent event) {
        if (event.getClass() == StartGameEvent.class) reactOnStartGameEvent(event);

        else if (event.getClass() == RequestProduceOrderEvent.class) reactOnRequestProduceOrderEvent(event);

        else if (event.getClass() == RequestLevelUpEvent.class) reactOnRequestLevelUpEvent(event);

        else if (event.getClass() == RequestBuySupplierEvent.class) reactOnRequestBuySupplierEvent(event);

        else if (event.getClass() == RequestLearnRecipeEvent.class) reactOnRequestLearnRecipeEvent(event);

        else if (event.getClass() == SuccessProduceOrderEvent.class) reactOnSuccessProduceOrderEvent(event);

        else if (event.getClass() == NotifyEvent.class) reactOnNotifyEvent(event);

        else if (event.getClass() == SaveSnapshotRoomEvent.class) reactOnSaveSnapshotRoomEvent(event);

        else if (event.getClass() == ReStockEvent.class) reactOnReStockEvent(event);

        else if (event.getClass() == OrderGenerateEvent.class) reactOnOrderGenerateEvent(event);

        else if (event.getClass() == SupplierGenerateEvent.class) reactOnSupplierGenerateEvent(event);

        else throw new RuntimeException("[ActionsMediator] :: Event doesn't exist in this Mediator.");
    }

    private void reactOnStartGameEvent(IEvent event) {
        ScreenManager screenManager = managerFacade.getManager(ScreenManager.class);
        GameManager gameManager = managerFacade.getManager(GameManager.class);

        screenManager.reactOnStartGameEvent(event);
        gameManager.reactOnStartGameEvent(event);

        PizzariaServiceImpl pizzariaService = serviceFacade.getService(PizzariaServiceImpl.class);
        StockServiceImpl stockService = serviceFacade.getService(StockServiceImpl.class);
        TokenShopServiceImpl tokenShopService = serviceFacade.getService(TokenShopServiceImpl.class);
        UpgradeServiceImpl upgradeService = serviceFacade.getService(UpgradeServiceImpl.class);

        pizzariaService.reactOnStartGameEvent(event);
        stockService.reactOnStartGameEvent(event);
        tokenShopService.reactOnStartGameEvent(event);
        upgradeService.reactOnStartGameEvent(event);

        PizzariaController pizzariaController = controllerFacade.getController(PizzariaController.class);
        StockController stockController = controllerFacade.getController(StockController.class);
        TokenShopController tokenShopController = controllerFacade.getController(TokenShopController.class);
        UpgradeController upgradeController = controllerFacade.getController(UpgradeController.class);

        pizzariaController.reactOnStartGameEvent(event);
        stockController.reactOnStartGameEvent(event);
        tokenShopController.reactOnStartGameEvent(event);
        upgradeController.reactOnStartGameEvent(event);

        GenItemStockFlux genItemStockFlux = fluxFacade.getFlux(GenItemStockFlux.class);
        GenSupplierFlux genSupplierFlux = fluxFacade.getFlux(GenSupplierFlux.class);
        GenOrderFlux genOrderFlux = fluxFacade.getFlux(GenOrderFlux.class);
        AutoSaveFlux autoSaveFlux = fluxFacade.getFlux(AutoSaveFlux.class);

        genItemStockFlux.reactOnStartGameEvent(event);
        genSupplierFlux.reactOnStartGameEvent(event);
        genOrderFlux.reactOnStartGameEvent(event);
        autoSaveFlux.reactOnStartGameEvent(event);
    }

    private void reactOnRequestProduceOrderEvent(IEvent event) {
        StockServiceImpl stockService = serviceFacade.getService(StockServiceImpl.class);
        stockService.reactOnRequestProduceOrderEvent(event);
    }

    private void reactOnRequestLevelUpEvent(IEvent event) {
        PizzariaServiceImpl pizzariaService = serviceFacade.getService(PizzariaServiceImpl.class);
        pizzariaService.reactOnRequestLevelUpEvent(event);
    }

    private void reactOnRequestBuySupplierEvent(IEvent event) {
        PizzariaServiceImpl pizzariaService = serviceFacade.getService(PizzariaServiceImpl.class);
        pizzariaService.reactOnRequestBuySupplierEvent(event);
    }

    private void reactOnRequestLearnRecipeEvent(IEvent event) {
        PizzariaServiceImpl pizzariaService = serviceFacade.getService(PizzariaServiceImpl.class);
        pizzariaService.reactOnRequestLearnRecipeEvent(event);
    }

    private void reactOnSuccessProduceOrderEvent(IEvent event) {
        PizzariaServiceImpl pizzariaService = serviceFacade.getService(PizzariaServiceImpl.class);

        pizzariaService.reactOnSuccessProduceOrderEvent(event);
    }

    private void reactOnNotifyEvent(IEvent event) {
        PizzariaController pizzariaController = controllerFacade.getController(PizzariaController.class);
        pizzariaController.reactOnNotifyEvent(event);

        NotificationController notificationController = controllerFacade.getController(NotificationController.class);
        notificationController.reactOnNotifyEvent(event);
    }

    private void reactOnSaveSnapshotRoomEvent(IEvent event) {
        GameManager gameManager = managerFacade.getManager(GameManager.class);
        gameManager.reactOnSaveSnapshotRoomEvent(event);

        AutoSaveFlux autoSaveFlux = fluxFacade.getFlux(AutoSaveFlux.class);
        autoSaveFlux.reactOnSaveSnapshotRoomEvent(event);
    }

    private void reactOnReStockEvent(IEvent event) {
        StockServiceImpl stockService = serviceFacade.getService(StockServiceImpl.class);
        stockService.reactOnRestockEvent(event);

        GenItemStockFlux genItemStockFlux = fluxFacade.getFlux(GenItemStockFlux.class);
        genItemStockFlux.reactOnRestockEvent(event);
    }
    private void reactOnOrderGenerateEvent(IEvent event) {
        PizzariaServiceImpl pizzariaService = serviceFacade.getService(PizzariaServiceImpl.class);
        pizzariaService.reactOnOrderGenerateEvent(event);

        GenOrderFlux genOrderFlux = fluxFacade.getFlux(GenOrderFlux.class);
        genOrderFlux.reactOnOrderGenerateEvent(event);
    }

    private void reactOnSupplierGenerateEvent(IEvent event) {
        TokenShopServiceImpl tokenShopService = serviceFacade.getService(TokenShopServiceImpl.class);
        tokenShopService.reactOnSupplierGenerateEvent(event);

        GenSupplierFlux genSupplierFlux = fluxFacade.getFlux(GenSupplierFlux.class);
        genSupplierFlux.reactOnSupplierGenerateEvent(event);
    }
}
