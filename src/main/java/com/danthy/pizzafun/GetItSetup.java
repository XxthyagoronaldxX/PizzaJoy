package com.danthy.pizzafun;

import com.danthy.pizzafun.app.controllers.HomeController;
import com.danthy.pizzafun.app.controllers.RoomController;
import com.danthy.pizzafun.app.enums.ScreenType;
import com.danthy.pizzafun.app.handles.GenItemStockThreadHandle;
import com.danthy.pizzafun.app.handles.GenOrderThreadHandle;
import com.danthy.pizzafun.app.handles.GenSupplierThreadHandle;
import com.danthy.pizzafun.app.logic.EventPublisher;
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

        RoomController roomController = FxmlUtil.controllerFromLoader(roomLoader);
        HomeController homeController = FxmlUtil.controllerFromLoader(homeLoader);

        roomController.setEventPublisher(eventPublisher);
        homeController.setEventPublisher(eventPublisher);

        ScreenManager screenManager = ScreenManager
                .build(stage)
                .addScreen(ScreenType.HOME, homeScene)
                .addScreen(ScreenType.ROOM, roomScene)
                .setInit(ScreenType.HOME);

        eventPublisher
                .addListener(roomController)
                .addListener(screenManager);

        GenSupplierThreadHandle genSupplierThreadHandle = new GenSupplierThreadHandle();
        GenOrderThreadHandle genOrderThreadHandle = new GenOrderThreadHandle();
        GenItemStockThreadHandle genItemStockThreadHandle = new GenItemStockThreadHandle();

        GameThreadManager gameThreadManager = GameThreadManager
                .build()
                .addThread(genSupplierThreadHandle)
                .addThread(genOrderThreadHandle)
                .addThread(genItemStockThreadHandle);

        getIt.addSingleton(eventPublisher);
        getIt.addSingleton(screenManager);
        getIt.addSingleton(gameThreadManager);
        getIt.addSingleton(roomController);
        getIt.addSingleton(homeController);
        getIt.addSingleton(stage);
    }
}
