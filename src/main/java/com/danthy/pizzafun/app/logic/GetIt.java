package com.danthy.pizzafun.app.logic;

import java.util.HashMap;
import java.util.Map;

public class GetIt {

    private static GetIt I;
    private final Map<Class, Object> instances;

    private GetIt() {
        this.instances = new HashMap<>();
    }

    public static GetIt getInstance() {
        if (GetIt.I == null) {
            GetIt.I = new GetIt();
        }

        return GetIt.I;
    }

    public <T> GetIt addSingleton(T instance) {
        this.instances.put(instance.getClass(), instance);

        return this;
    }

    public <T> T find(Class<T> instanceClass) {
        return (T) this.instances.get(instanceClass);
    }
}

