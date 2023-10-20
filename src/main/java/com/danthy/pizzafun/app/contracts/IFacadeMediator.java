package com.danthy.pizzafun.app.contracts;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public interface IFacadeMediator<T> {

    void reactOn(IEvent event);

    IFacadeMediator<T> add(Flux flux);

    default Map<Class<? extends  IEvent>, Method> genMapEventToReactMethodFromClass(Class<?> clazz) {
        Map<Class<? extends IEvent>, Method> mapEventToReactMethod = new HashMap<>();

        for (Method method : clazz.getDeclaredMethods()) {
            method.setAccessible(true);

            ReactOn annotation = method.getAnnotation(ReactOn.class);

            if (annotation != null) {
                Class<? extends IEvent> event = annotation.value();

                mapEventToReactMethod.put(event, method);
            }
        }

        return mapEventToReactMethod;
    }
}
