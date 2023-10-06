package com.danthy.pizzafun;

import com.danthy.pizzafun.app.utils.ApplicationProperties;
import com.danthy.pizzafun.app.utils.GetIt;
import com.danthy.pizzafun.domain.data.PostConstruct;
import javafx.application.Application;
import javafx.stage.Stage;

public class PizzaFunApplication extends Application {
    @Override
    public void start(Stage stage)  {
        stage.setTitle("PizzaFun");
        stage.setResizable(false);

        PostConstruct.genModels();
        ApplicationProperties.init();
        GetItSetup.setup(stage);
        GetIt.getInstance().find(ScreenManager.class).start();
    }

    public static void main(String[] args) {
        launch();
    }
}