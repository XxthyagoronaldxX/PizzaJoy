package com.danthy.pizzafun.newFeature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public interface IMapper {
    default void update(ITest test) {
        mapEventToMethod(test);
    }

    default void mapEventToMethod(ITest test) {
        String className = test.getClass().getSimpleName();

        for (Method method : getClass().getMethods()) {
            if (method.getName().equals("on" + className)) {
                try {
                    method.invoke(this);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
