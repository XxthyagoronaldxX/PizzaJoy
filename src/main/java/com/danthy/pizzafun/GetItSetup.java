package com.danthy.pizzafun;

import com.danthy.pizzafun.app.controllers.*;
import com.danthy.pizzafun.app.enums.ScreenType;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.services.IStockService;
import com.danthy.pizzafun.app.services.ITokenShopService;
import com.danthy.pizzafun.app.services.IUpgradeService;
import com.danthy.pizzafun.app.services.implementations.UpgradeServiceImpl;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
import com.danthy.pizzafun.app.services.implementations.TokenShopServiceImpl;
import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.logic.GetIt;
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

        // CONFIGURING SCREEN MANAGER
        ScreenManager screenManager = ScreenManager
                .build(stage)
                .addScreen(ScreenType.HOME, homeScene)
                .addScreen(ScreenType.ROOM, roomScene)
                .setInit(ScreenType.HOME);

        // GETTING SERVICES TO SEND TO GETIT [SINGLETON]
        ITokenShopService tokenShopService = new TokenShopServiceImpl();
        IPizzariaService roomService = new PizzariaServiceImpl();
        IStockService stockService = new StockServiceImpl();
        IUpgradeService upgradeService = new UpgradeServiceImpl();

        // INITIALIZING GAME MANAGER (CLASS USED TO MANAGE THE CONTINUOUS "THREADS" THAT ARE WORKING ON START GAME)
        GameManager gameManager = new GameManager();

        // INITIALIZING LISTENERS
        eventPublisher
                .addListener(tokenShopService)
                .addListener(roomService)
                .addListener(stockService)
                .addListener(upgradeService)
                .addListener(upgradeController)
                .addListener(pizzariaController)
                .addListener(tokenShopController)
                .addListener(stockController)
                .addListener(gameManager)
                .addListener(screenManager);

        // INITIALIZING SINGLETONS
        getIt.addSingleton(tokenShopService)
                .addSingleton(stockService)
                .addSingleton(roomService)
                .addSingleton(upgradeService)
                .addSingleton(screenManager)
                .addSingleton(pizzariaController)
                .addSingleton(homeController)
                .addSingleton(stage);
    }
}
