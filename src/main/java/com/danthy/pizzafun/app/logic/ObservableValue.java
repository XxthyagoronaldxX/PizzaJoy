package com.danthy.pizzafun.app.logic;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public class ObservableValue<T> {
    private final Property<T> value;

    public ObservableValue(T value) {
        this.value = new SimpleObjectProperty<>(value);
    }

    public T getValue() {
        return value.getValue();
    }

    public Property<T> getProperty() {
        return value;
    }
}
