package com.danthy.pizzafun.domain.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StockModel {

    private UUID id;
    private final List<ItemStockModel> itemStockModels;
    private int currentWeight;
    private int totalWeight;

    public StockModel(int totalWeight) {
        this.id = UUID.randomUUID();
        itemStockModels = new ArrayList<>();
        this.totalWeight = totalWeight;
        this.currentWeight = 0;
    }

    public List<ItemStockModel> getItemStockModels() {
        return itemStockModels;
    }

    public StockModel addItemStockModel(ItemStockModel itemStockModel) {
        this.itemStockModels.add(itemStockModel);

        return this;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String showItems() {
        return this.itemStockModels.stream()
                .map(itemStock -> {
                    return String.format(
                            "%s [%sx]",
                            itemStock.getItemModel().getName(),
                            String.valueOf(itemStock.getQuantity())
                    );
                })
                .reduce("", (acc, value) -> acc + value + "\n");
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
        StockModel other = (StockModel) obj;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        return true;
    }
}
