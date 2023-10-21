package com.danthy.pizzafun;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.managers.ScreenManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.UUID;

public class PizzaFunApplication extends Application {
    @Override
    public void start(Stage stage)  {
        stage.setTitle("PizzaFun");
        ApplicationProperties.init();
        GetItSetup.setup(stage);
        GetIt.getInstance().find(ScreenManager.class).start();
    }

    public static void main(String[] args) {launch();}
}