package com.danthy.pizzafun;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.logic.GetIt;
import javafx.application.Application;
import javafx.stage.Stage;

public class PizzaFunApplication extends Application {
    @Override
    public void start(Stage stage)  {
        stage.setTitle("PizzaFun");
        ApplicationProperties.init();
        GetItSetup.setup(stage);
        GetIt.getInstance().find(ScreenManager.class).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}