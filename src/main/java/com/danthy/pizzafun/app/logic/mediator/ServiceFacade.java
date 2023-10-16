package com.danthy.pizzafun.app.logic.mediator;

import com.danthy.pizzafun.app.contracts.IService;

import java.util.HashMap;
import java.util.Map;

public class ServiceFacade {
    private final Map<Class, IService> serviceMap;

    private ServiceFacade() {
        this.serviceMap = new HashMap<>();
    };

    public static ServiceFacade build() {
        return new ServiceFacade();
    }

    public ServiceFacade addService(IService IService) {
        this.serviceMap.put(IService.getClass(), IService);

        return this;
    }

    public <T extends IService> T getService(Class clazz) {
        return (T) this.serviceMap.get(clazz);
    }
}
