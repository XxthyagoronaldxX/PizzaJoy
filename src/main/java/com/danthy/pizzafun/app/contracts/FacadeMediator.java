package com.danthy.pizzafun.app.contracts;

import com.danthy.pizzafun.app.logic.pojo.ObjectReactMethodPojo;

import java.lang.reflect.Method;
import java.util.*;

public abstract class FacadeMediator<T> {
    private final Map<Class<? extends IEvent>, List<ObjectReactMethodPojo>> mapEventToObjects;

    protected FacadeMediator() {
        this.mapEventToObjects = new HashMap<>();
    }

    public void reactOn(IEvent event) {
        List<ObjectReactMethodPojo> objectList = mapEventToObjects.get(event.getClass());

        if (objectList == null) return;

        objectList.forEach(object -> {
            object.invoke(event);
        });
    }

    public FacadeMediator add(T object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            method.setAccessible(true);

            ReactOn annotation = method.getAnnotation(ReactOn.class);

            if (annotation != null) {
                Class<? extends IEvent> event = annotation.value();

                List<ObjectReactMethodPojo> objectPojoList = mapEventToObjects.get(event);
                ObjectReactMethodPojo newObjectPojo = new ObjectReactMethodPojo(object, method);

                if (objectPojoList == null) {
                    mapEventToObjects.put(event, new ArrayList(Collections.singleton(newObjectPojo)));
                } else {
                    objectPojoList.add(newObjectPojo);
                }
            }
        }

        return this;
    }
}
