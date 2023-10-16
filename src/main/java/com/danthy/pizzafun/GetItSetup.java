package com.danthy.pizzafun;

import com.danthy.pizzafun.app.controllers.*;
import com.danthy.pizzafun.app.enums.ScreenType;
import com.danthy.pizzafun.app.logic.*;
import com.danthy.pizzafun.app.logic.mediator.*;
import com.danthy.pizzafun.app.managers.GameManager;
import com.danthy.pizzafun.app.managers.ScreenManager;
import com.danthy.pizzafun.app.services.implementations.IUpgradeServiceImpl;
import com.danthy.pizzafun.app.services.implementations.IPizzariaServiceImpl;
import com.danthy.pizzafun.app.services.implementations.IStockServiceImpl;
import com.danthy.pizzafun.app.services.implementations.ITokenShopServiceImpl;
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
        ITokenShopServiceImpl tokenShopService = new ITokenShopServiceImpl(eventPublisher);
        IPizzariaServiceImpl pizzariaService = new IPizzariaServiceImpl(eventPublisher);
        IStockServiceImpl stockService = new IStockServiceImpl(eventPublisher);
        IUpgradeServiceImpl upgradeService = new IUpgradeServiceImpl(eventPublisher);

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
        ScreenManager screenManager = ScreenManager
                .build(stage)
                .addScreen(ScreenType.HOME, homeScene)
                .addScreen(ScreenType.ROOM, roomScene)
                .setInit(ScreenType.HOME);

        // INITIALIZING GAME MANAGER (CLASS USED TO MANAGE THE CONTINUOUS "THREADS" THAT ARE WORKING ON START GAME)
        GameManager gameManager = new GameManager();

        // INITIALIZING MEDIATOR
        ControllerFacade controllerFacade = ControllerFacade
                .build()
                .addController(pizzariaController)
                .addController(homeController)
                .addController(tokenShopController)
                .addController(stockController)
                .addController(upgradeController)
                .addController(notificationController);

        ServiceFacade serviceFacade = ServiceFacade
                .build()
                .addService(stockService)
                .addService(tokenShopService)
                .addService(pizzariaService)
                .addService(upgradeService);

        ManagerFacade managerFacade = ManagerFacade
                .build()
                .addManager(gameManager)
                .addManager(screenManager);

        AutoSaveFlux autoSaveFlux = new AutoSaveFlux();
        GenSupplierFlux genSupplierFlux = new GenSupplierFlux();
        GenOrderFlux genOrderFlux = new GenOrderFlux(pizzariaService);
        GenItemStockFlux genItemStockFlux = new GenItemStockFlux(stockService, tokenShopService);

        FluxFacade fluxFacade = FluxFacade
                .build()
                .addFlux(autoSaveFlux)
                .addFlux(genSupplierFlux)
                .addFlux(genOrderFlux)
                .addFlux(genItemStockFlux);

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
