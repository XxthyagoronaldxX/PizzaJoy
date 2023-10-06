package com.danthy.pizzafun;

import com.danthy.pizzafun.app.controllers.HomeController;
import com.danthy.pizzafun.app.controllers.RoomController;
import com.danthy.pizzafun.app.enums.ScreenType;
import com.danthy.pizzafun.app.handles.GenOrderThreadHandle;
import com.danthy.pizzafun.app.utils.EventPublisher;
import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.utils.GetIt;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GetItSetup {
    public static void setup(Stage stage) {
        GetIt getIt = GetIt.getInstance();

        EventPublisher eventPublisher = new EventPublisher();

        FXMLLoader roomLoader = FxmlUtil.loaderFromName("room-view", PizzaFunApplication.class);
        FXMLLoader homeLoader = FxmlUtil.loaderFromName("home-view", PizzaFunApplication.class);

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

        Thread genOrderThreadHandle = new GenOrderThreadHandle(roomController);

        getIt.addSingleton(eventPublisher);
        getIt.addSingleton(screenManager);
        getIt.addSingleton(genOrderThreadHandle);
        getIt.addSingleton(roomController);
        getIt.addSingleton(homeController);
        getIt.addSingleton(stage);
    }
}
