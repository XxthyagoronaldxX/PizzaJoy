package com.danthy.pizzafun.app.logic.mediator;

import com.danthy.pizzafun.app.contracts.IManager;

import java.util.HashMap;
import java.util.Map;

public class ManagerFacade {
    private final Map<Class, IManager> managerMap;

    private ManagerFacade() {
        this.managerMap = new HashMap<>();
    }

    public static ManagerFacade build() {
        return new ManagerFacade();
    }

    public ManagerFacade addManager(IManager manager) {
        this.managerMap.put(manager.getClass(), manager);

        return this;
    }

    public <T extends IManager> T getManager(Class clazz) {
        return (T) this.managerMap.get(clazz);
    }
}
