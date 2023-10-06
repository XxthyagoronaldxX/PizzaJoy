package com.danthy.pizzafun.domain.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PizzaEdgeModel {

    private UUID id;
    private final List<ItemPizzaModel> itemPizzaModels;
    private String name;

    public PizzaEdgeModel() {
        this.itemPizzaModels = new ArrayList<>();
    }

    public static PizzaEdgeModel build() {
        return new PizzaEdgeModel();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public List<ItemPizzaModel> getItemPizzaModels() {
        return itemPizzaModels;
    }

    public PizzaEdgeModel addItem(ItemPizzaModel itemPizzaModel) {
        this.itemPizzaModels.add(itemPizzaModel);

        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PizzaEdgeModel other = (PizzaEdgeModel) obj;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        return true;
    }
}
