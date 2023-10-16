package com.danthy.pizzafun.app.logic.mediator;

import com.danthy.pizzafun.app.contracts.Service;

import java.util.HashMap;
import java.util.Map;

public class ServiceFacade {
    private final Map<Class, Service> serviceMap;

    private ServiceFacade() {
        this.serviceMap = new HashMap<>();
    };

    public static ServiceFacade build() {
        return new ServiceFacade();
    }

    public ServiceFacade addService(Service service) {
        this.serviceMap.put(service.getClass(), service);

        return this;
    }

    public <T extends Service> T getService(Class clazz) {
        return (T) this.serviceMap.get(clazz);
    }
}
