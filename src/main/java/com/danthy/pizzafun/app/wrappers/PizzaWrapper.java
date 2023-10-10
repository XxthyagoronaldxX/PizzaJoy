package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapperModel;
import com.danthy.pizzafun.domain.models.PizzaModel;

public class PizzaWrapper implements IWrapperModel<PizzaModel> {
    private final PizzaModel pizzaModel;

    public PizzaWrapper(PizzaModel pizzaModel) {
        this.pizzaModel = pizzaModel;
    }

    @Override
    public PizzaModel getWrapped() {
        return pizzaModel;
    }

}
