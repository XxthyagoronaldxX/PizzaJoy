package com.danthy.pizzafun.app.contracts;

import com.danthy.pizzafun.domain.errors.MethodNotFoundException;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

public interface IListener {

    @SneakyThrows
    default void update(IEvent event) {
        for (Method method : getClass().getDeclaredMethods()) {
            method.setAccessible(true);

            EventMap annotation = method.getAnnotation(EventMap.class);

            if (annotation != null && annotation.value().equals(event.getClass())) {
                method.invoke(this, event);
                return;
            }
        }
    }
}
