package com.danthy.pizzafun.domain.models;

import java.util.ArrayList;
import java.util.List;

public class PizzaModel {

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

    public float getPrice() {
        return price;
    }

    public PizzaModel setPrice(float price) {
        this.price = price;

        return this;
    }

    public String getName() {
        return name;
    }

    public PizzaModel setName(String name) {
        this.name = name;

        return this;
    }

    public List<ItemPizzaModel> getItemPizzaModels() {
        return itemPizzaModels;
    }

    public PizzaModel addItemPizzaModel(ItemPizzaModel itemPizzaModel) {
        this.itemPizzaModels.add(itemPizzaModel);

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

    public PizzaEdgeModel getPizzaEdgeModel() {
        return pizzaEdgeModel;
    }

    public void setPizzaEdgeModel(PizzaEdgeModel pizzaEdgeModel) {
        this.pizzaEdgeModel = pizzaEdgeModel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PizzaModel other = (PizzaModel) obj;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
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
