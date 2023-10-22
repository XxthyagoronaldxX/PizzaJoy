package com.danthy.pizzafun;

import com.danthy.pizzafun.app.controllers.home.HomeController;
import com.danthy.pizzafun.app.controllers.pizzaria.PizzariaController;
import com.danthy.pizzafun.app.controllers.pizzaria.subviews.NotificationController;
import com.danthy.pizzafun.app.controllers.pizzaria.subviews.StockController;
import com.danthy.pizzafun.app.controllers.pizzaria.subviews.TokenShopController;
import com.danthy.pizzafun.app.controllers.pizzaria.subviews.UpgradeController;
import com.danthy.pizzafun.app.enums.ScreenType;
import com.danthy.pizzafun.app.logic.*;
import com.danthy.pizzafun.app.logic.mediator.*;
import com.danthy.pizzafun.app.managers.GameManager;
import com.danthy.pizzafun.app.managers.ScreenBuilder;
import com.danthy.pizzafun.app.managers.ScreenManager;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.services.IStockService;
import com.danthy.pizzafun.app.services.ITokenShopService;
import com.danthy.pizzafun.app.services.IUpgradeService;
import com.danthy.pizzafun.app.services.implementations.UpgradeServiceImpl;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
import com.danthy.pizzafun.app.services.implementations.TokenShopServiceImpl;
import com.danthy.pizzafun.app.fluxs.AutoSaveFlux;
import com.danthy.pizzafun.app.fluxs.GenItemStockFlux;
import com.danthy.pizzafun.app.fluxs.GenOrderFlux;
import com.danthy.pizzafun.app.fluxs.GenSupplierFlux;
import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import com.danthy.pizzafun.domain.data.ItemXmlData;
import com.danthy.pizzafun.domain.data.PizzaXmlData;
import com.danthy.pizzafun.domain.data.RoomSavesXmlData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GetItSetup {
    public static void setup(Stage stage) {
        // PRE CONFIGURATION [DON'T CHANGE THIS ORDER!]
        GetIt getIt = GetIt.getInstance();

        EventPublisher eventPublisher = new EventPublisher();
        getIt.addSingleton(eventPublisher);
        getIt.addSingleton(PizzaXmlData.getFromXml());
        getIt.addSingleton(ItemXmlData.getFromXml());
        getIt.addSingleton(RoomSavesXmlData.getFromXml());

        // GETTING SERVICES TO SEND TO GETIT [SINGLETON]
        ITokenShopService tokenShopService = new TokenShopServiceImpl();
        IPizzariaService pizzariaService = new PizzariaServiceImpl();
        IStockService stockService = new StockServiceImpl();
        IUpgradeService upgradeService = new UpgradeServiceImpl();

        // CONFIGURING FXML FILES
        FXMLLoader roomLoader = FxmlUtil.loaderFromName(PathFxmlUtil.ROOM_VIEW, PizzaFunApplication.class);
        FXMLLoader homeLoader = FxmlUtil.loaderFromName(PathFxmlUtil.HOME_VIEW, PizzaFunApplication.class);

        Scene roomScene = FxmlUtil.sceneFromLoader(roomLoader, 1280, 720);
        Scene homeScene = FxmlUtil.sceneFromLoader(homeLoader, 820, 560);

        PizzariaController pizzariaController = FxmlUtil.controllerFromLoader(roomLoader);
        HomeController homeController = FxmlUtil.controllerFromLoader(homeLoader);
        TokenShopController tokenShopController = pizzariaController.tokenShopController;
        StockController stockController = pizzariaController.stockController;
        UpgradeController upgradeController = pizzariaController.upgradeController;
        NotificationController notificationController = pizzariaController.notificationController;

        // CONFIGURING SCREEN MANAGER
        ScreenManager screenManager = ScreenBuilder
                .builder()
                .setStage(stage)
                .addScreen(ScreenType.HOME, homeScene)
                .addScreen(ScreenType.ROOM, roomScene)
                .setInit(ScreenType.HOME)
                .build();

        // INITIALIZING GAME MANAGER (CLASS USED TO MANAGE THE CONTINUOUS "THREADS" THAT ARE WORKING ON START GAME)
        GameManager gameManager = new GameManager();

        // INITIALIZING MEDIATOR
        ControllerFacade controllerFacade = new ControllerFacade();

        controllerFacade.add(pizzariaController)
                .add(homeController)
                .add(tokenShopController)
                .add(stockController)
                .add(upgradeController)
                .add(notificationController);

        ServiceFacade serviceFacade = new ServiceFacade();

        serviceFacade.add(stockService)
                .add(tokenShopService)
                .add(pizzariaService)
                .add(upgradeService);

        ManagerFacade managerFacade = new ManagerFacade();

        managerFacade.add(gameManager)
                .add(screenManager);

        AutoSaveFlux autoSaveFlux = new AutoSaveFlux();
        GenSupplierFlux genSupplierFlux = new GenSupplierFlux();
        GenOrderFlux genOrderFlux = new GenOrderFlux(pizzariaService);
        GenItemStockFlux genItemStockFlux = new GenItemStockFlux(stockService, tokenShopService);

        FluxFacade fluxFacade = new FluxFacade();

        fluxFacade.add(autoSaveFlux)
                .add(genSupplierFlux)
                .add(genOrderFlux)
                .add(genItemStockFlux);

        ActionsMediator actionsMediator = new ActionsMediator(controllerFacade,serviceFacade, managerFacade, fluxFacade);

        // INITIALIZING LISTENERS
        eventPublisher
                .addListener(tokenShopService)
                .addListener(pizzariaService)
                .addListener(stockService)
                .addListener(upgradeService);

        // INITIALIZING SINGLETONS
        getIt.addSingleton(actionsMediator)
                .addSingleton(tokenShopService)
                .addSingleton(stockService)
                .addSingleton(pizzariaService)
                .addSingleton(upgradeService)
                .addSingleton(screenManager)
                .addSingleton(pizzariaController)
                .addSingleton(homeController)
                .addSingleton(stage);
    }
}
