package com.danthy.pizzafun;

import com.danthy.pizzafun.app.controllers.HomeController;
import com.danthy.pizzafun.app.controllers.PizzariaController;
import com.danthy.pizzafun.app.controllers.StockController;
import com.danthy.pizzafun.app.controllers.TokenShopController;
import com.danthy.pizzafun.app.enums.ScreenType;
import com.danthy.pizzafun.app.handles.GameManageHandle;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
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
        StockServiceImpl stockService = new StockServiceImpl();

        GameManageHandle gameManageHandle = new GameManageHandle();

        eventPublisher
                .addListener(tokenShopService)
                .addListener(roomService)
                .addListener(stockService)
                .addListener(pizzariaController)
                .addListener(tokenShopController)
                .addListener(stockController)
                .addListener(gameManageHandle)
                .addListener(screenManager);

        getIt.addSingleton(tokenShopService)
                .addSingleton(stockService)
                .addSingleton(roomService)
                .addSingleton(eventPublisher)
                .addSingleton(screenManager)
                .addSingleton(pizzariaController)
                .addSingleton(homeController)
                .addSingleton(stage);
    }
}
