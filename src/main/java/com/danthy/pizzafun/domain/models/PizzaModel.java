package com.danthy.pizzafun.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PizzaModel {
    @EqualsAndHashCode.Include
    private String name;

    private final List<ItemPizzaModel> itemPizzaModels;

    private float priceToSell;

    private float priceToBuyRecipe;

    private int timeInSecondsToLearn;

    private PizzaEdgeModel pizzaEdgeModel;

    public PizzaModel() {
        this.itemPizzaModels = new ArrayList<>();
    }

    public static PizzaModel build() {
        return new PizzaModel();
    }

    public PizzaModel setPriceToSell(float price) {
        this.priceToSell = price;

        return this;
    }

    public PizzaModel setTimeInSecondsToLearn(int seconds) {
        this.timeInSecondsToLearn = seconds;

        return this;
    }

    public PizzaModel setPriceToBuyRecipe(float price) {
        this.priceToBuyRecipe = price;

        return this;
    }

    public PizzaModel setName(String name) {
        this.name = name;

        return this;
    }

    public PizzaModel addItemPizzaModel(ItemModel itemModel, int quantity) {
        this.itemPizzaModels.add(new ItemPizzaModel(itemModel, quantity));

        return this;
    }

    public PizzaModel setEdge(PizzaEdgeModel pizzaEdgeModel) {
        this.pizzaEdgeModel = pizzaEdgeModel;

        return this;
    }
}
