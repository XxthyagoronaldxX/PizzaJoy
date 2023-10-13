package com.danthy.pizzafun.app.utils;

import com.danthy.pizzafun.PizzaFunApplication;
import com.danthy.pizzafun.app.contracts.IController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class FxmlUtil {
    public static FXMLLoader loaderFromName(String name, Class<?> getClass) {
        String file = String.format("%s.fxml", name);
        return new FXMLLoader(getClass.getResource(file));
    }

    public static FXMLLoader loaderFromName(String name) {
        String file = String.format("%s.fxml", name);
        return new FXMLLoader(PizzaFunApplication.class.getResource(file));
    }

    public static Scene sceneFromLoader(FXMLLoader loader, double width, double height) {
        try {
            return new Scene(loader.load(), width, height);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void loadFXMLInjectController(Object controller, String path) {
        FXMLLoader loader = loaderFromName(path);

        try {
            loader.setController(controller);
            loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static <T extends IController> T controllerFromLoader(FXMLLoader loader) {
        return loader.getController();
    }
}
