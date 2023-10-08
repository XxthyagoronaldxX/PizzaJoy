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

    private float price;

    private PizzaEdgeModel pizzaEdgeModel;

    public PizzaModel() {
        this.itemPizzaModels = new ArrayList<>();
    }

    public static PizzaModel build() {
        return new PizzaModel();
    }

    public PizzaModel setPrice(float price) {
        this.price = price;

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

    @Override
    public String toString() {
        return (
                "PizzaModel [name=" +
                        name +
                        ", itemPizzaModels=" +
                        itemPizzaModels +
                        ", price=" +
                        price +
                        ", pizzaEdgeModel=" +
                        pizzaEdgeModel +
                        "]"
        );
    }
}
