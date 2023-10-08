package com.danthy.pizzafun;

import com.danthy.pizzafun.app.controllers.HomeController;
import com.danthy.pizzafun.app.controllers.PizzariaController;
import com.danthy.pizzafun.app.controllers.StockController;
import com.danthy.pizzafun.app.controllers.TokenShopController;
import com.danthy.pizzafun.app.enums.ScreenType;
import com.danthy.pizzafun.app.handles.GenItemStockThreadHandle;
import com.danthy.pizzafun.app.handles.GenOrderThreadHandle;
import com.danthy.pizzafun.app.handles.GenSupplierThreadHandle;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.services.implementations.TokenShopServiceImpl;
import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GetItSetup {
    public static void setup(Stage stage) {
        GetIt getIt = GetIt.getInstance();

        EventPublisher eventPublisher = new EventPublisher();

        FXMLLoader roomLoader = FxmlUtil.loaderFromName(PathFxmlUtil.ROOM_VIEW, PizzaFunApplication.class);
        FXMLLoader homeLoader = FxmlUtil.loaderFromName(PathFxmlUtil.HOME_VIEW, PizzaFunApplication.class);

        Scene roomScene = FxmlUtil.sceneFromLoader(roomLoader, 1280, 720);
        Scene homeScene = FxmlUtil.sceneFromLoader(homeLoader, 820, 560);

        PizzariaController pizzariaController = FxmlUtil.controllerFromLoader(roomLoader);
        HomeController homeController = FxmlUtil.controllerFromLoader(homeLoader);
        TokenShopController tokenShopController = pizzariaController.tokenShopController;
        StockController stockController = pizzariaController.stockController;

        pizzariaController.setEventPublisher(eventPublisher);
        homeController.setEventPublisher(eventPublisher);
        tokenShopController.setEventPublisher(eventPublisher);
        stockController.setEventPublisher(eventPublisher);

        ScreenManager screenManager = ScreenManager
                .build(stage)
                .addScreen(ScreenType.HOME, homeScene)
                .addScreen(ScreenType.ROOM, roomScene)
                .setInit(ScreenType.HOME);

        TokenShopServiceImpl tokenShopService = new TokenShopServiceImpl();
        PizzariaServiceImpl roomService = new PizzariaServiceImpl();

        GenSupplierThreadHandle genSupplierThreadHandle = new GenSupplierThreadHandle();
        genSupplierThreadHandle.setDaemon(true);

        GenOrderThreadHandle genOrderThreadHandle = new GenOrderThreadHandle();
        genOrderThreadHandle.setDaemon(true);

        GenItemStockThreadHandle genItemStockThreadHandle = new GenItemStockThreadHandle();
        genItemStockThreadHandle.setDaemon(true);

        GameThreadManager gameThreadManager = GameThreadManager
                .build()
                .addThread(genSupplierThreadHandle)
                .addThread(genOrderThreadHandle)
                .addThread(genItemStockThreadHandle);

        eventPublisher
                .addListener(tokenShopService)
                .addListener(roomService)
                .addListener(genItemStockThreadHandle)
                .addListener(genOrderThreadHandle)
                .addListener(pizzariaController)
                .addListener(tokenShopController)
                .addListener(stockController)
                .addListener(gameThreadManager)
                .addListener(screenManager);

        getIt.addSingleton(tokenShopService);
        getIt.addSingleton(roomService);
        getIt.addSingleton(eventPublisher);
        getIt.addSingleton(screenManager);
        getIt.addSingleton(gameThreadManager);
        getIt.addSingleton(pizzariaController);
        getIt.addSingleton(homeController);
        getIt.addSingleton(stage);
    }
}
