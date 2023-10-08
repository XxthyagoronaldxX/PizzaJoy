package com.danthy.pizzafun.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPizzaModel {
    @EqualsAndHashCode.Include
    private UUID id;

    private ItemModel item;

    private int quantity;

    public ItemPizzaModel(ItemModel item, int quantity) {
        this.id = UUID.randomUUID();
        this.item = item;
        this.quantity = quantity;
    }
}
