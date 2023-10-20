package com.danthy.pizzafun.app.logic.pojo;

import com.danthy.pizzafun.app.contracts.IEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

@AllArgsConstructor
@Getter
@Setter
public class ObjectReactMethodPojo {
    private Object object;
    private Method method;

    @SneakyThrows
    public void invoke(IEvent event) {
        method.invoke(object, event);
    }
}
