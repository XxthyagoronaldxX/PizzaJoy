package com.danthy.pizzafun.domain.data;


import com.danthy.pizzafun.domain.models.PizzaModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PizzaDataSingleton {

    private static final PizzaDataSingleton I = new PizzaDataSingleton();
    private final List<PizzaModel> pizzaModels;

    public PizzaDataSingleton() {
        this.pizzaModels = new ArrayList<>();
    }

    public static PizzaDataSingleton getInstance() {
        return PizzaDataSingleton.I;
    }

    public List<PizzaModel> getPizzaModels() {
        return pizzaModels;
    }

    public PizzaModel getRandomPizza() {
        return pizzaModels.get((int) Math.floor(Math.random() * (pizzaModels.size() - 1)));
    }

    public void addItem(PizzaModel itemModel) {
        this.pizzaModels.add(itemModel);
    }

    public void addAllItem(PizzaModel... itemModels) {
        this.pizzaModels.addAll(Arrays.asList(itemModels));
    }
}
