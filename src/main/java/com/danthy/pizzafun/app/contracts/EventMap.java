package com.danthy.pizzafun.app.contracts;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventMap {
    Class<?> value();
}
