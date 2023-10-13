package com.danthy.pizzafun.domain.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StockModel {
    @EqualsAndHashCode.Include
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

    public StockModel addItemStockModel(ItemStockModel itemStockModel) {
        this.itemStockModels.add(itemStockModel);

        return this;
    }
}
