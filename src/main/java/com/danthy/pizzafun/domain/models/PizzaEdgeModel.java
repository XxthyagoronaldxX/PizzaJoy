package com.danthy.pizzafun.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PizzaEdgeModel {
    @EqualsAndHashCode.Include
    private UUID id;

    private final List<ItemPizzaModel> itemPizzaModels;

    private String name;

    public PizzaEdgeModel() {
        this.itemPizzaModels = new ArrayList<>();
    }

    public static PizzaEdgeModel build() {
        return new PizzaEdgeModel();
    }

    public PizzaEdgeModel addItem(ItemPizzaModel itemPizzaModel) {
        this.itemPizzaModels.add(itemPizzaModel);

        return this;
    }
}
