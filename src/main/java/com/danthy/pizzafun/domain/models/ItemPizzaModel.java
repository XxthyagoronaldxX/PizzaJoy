package com.danthy.pizzafun.domain.models;

import java.util.UUID;

public class ItemPizzaModel {

    private UUID id;
    private ItemModel item;
    private int quantity;

    public ItemPizzaModel(ItemModel item, int quantity) {
        this.id = UUID.randomUUID();
        this.item = item;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ItemModel getItem() {
        return item;
    }

    public void setItem(ItemModel item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
