package com.danthy.pizzafun.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ItemModel {
    @EqualsAndHashCode.Include
    private String name;
    private int weight;

    public ItemModel(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
}
