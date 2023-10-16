package com.danthy.pizzafun.app.logic.mediator;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.controllers.HomeController;
import com.danthy.pizzafun.app.controllers.NotificationController;
import com.danthy.pizzafun.app.controllers.TokenShopController;

import java.util.HashMap;
import java.util.Map;

public class ControllerFacade {
    private final Map<Class, IController> controllerMap;

    private ControllerFacade() {
        this.controllerMap = new HashMap<>();
    }

    public static ControllerFacade build() {
        return new ControllerFacade();
    }

    public ControllerFacade addController(IController controller) {
        this.controllerMap.put(controller.getClass(), controller);

        return this;
    }

    public <T extends IController> T getController(Class clazz) {
        return (T) controllerMap.get(clazz);
    }
}
