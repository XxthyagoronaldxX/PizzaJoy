package com.danthy.pizzafun.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoomModel {
    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    private double balance;

    private int tokens;

    private StockModel stockModel;

    private SupplierModel supplierModel;

    private List<PizzaModel> pizzaModels;

    private final List<OrderModel> orderModels;

    public RoomModel(String name) {
        this.id = UUID.randomUUID();
        this.pizzaModels = new ArrayList<>();
        this.orderModels = new ArrayList<>();
        this.balance = 0.0;
        this.name = name;
    }
}
