package com.danthy.pizzafun.domain.data;

import com.danthy.pizzafun.domain.models.PizzaEdgeModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PizzaEdgeDataSingleton {

    private static final PizzaEdgeDataSingleton I = new PizzaEdgeDataSingleton();
    private final List<PizzaEdgeModel> pizzaEdgeModels;

    public PizzaEdgeDataSingleton() {
        this.pizzaEdgeModels = new ArrayList<>();
    }

    public static PizzaEdgeDataSingleton getInstance() {
        return PizzaEdgeDataSingleton.I;
    }

    public List<PizzaEdgeModel> getItemModels() {
        return pizzaEdgeModels;
    }

    public void addItem(PizzaEdgeModel pizzaEdgeModel) {
        this.pizzaEdgeModels.add(pizzaEdgeModel);
    }

    public void addAllItem(PizzaEdgeModel... pizzaEdgeModels) {
        this.pizzaEdgeModels.addAll(Arrays.asList(pizzaEdgeModels));
    }
}
