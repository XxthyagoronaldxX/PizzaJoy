package com.danthy.pizzafun;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.domain.data.PostConstruct;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PizzaFunApplication extends Application {
    @Override
    public void start(Stage stage)  {
        stage.setTitle("PizzaFun");
        ApplicationProperties.init();
        PostConstruct.genModels();
        GetItSetup.setup(stage);
        GetIt.getInstance().find(ScreenManager.class).start();
    }

    public static void main(String[] args) {
        launch();
    }
}