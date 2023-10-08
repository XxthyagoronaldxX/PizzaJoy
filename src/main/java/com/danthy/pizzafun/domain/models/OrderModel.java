package com.danthy.pizzafun.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderModel {
    @EqualsAndHashCode.Include
    private UUID id;

    private NpcModel npcModel;

    private PizzaModel pizzaModel;

    public OrderModel(NpcModel npcModel, PizzaModel pizzaModel) {
        this.id = UUID.randomUUID();
        this.npcModel = npcModel;
        this.pizzaModel = pizzaModel;
    }

    @Override
    public String toString() {
        return this.pizzaModel.getItemPizzaModels()
                .stream()
                .map(item
                        -> item.getItem().getName() + " [" + item.getQuantity() + "x]\n"
                )
                .reduce("", (acc, sItem) -> acc + sItem);
    }
}
