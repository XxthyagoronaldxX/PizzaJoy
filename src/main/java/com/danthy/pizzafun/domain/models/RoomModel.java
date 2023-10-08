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

    public RoomModel(String name, StockModel stockModel) {
        this.id = UUID.randomUUID();
        this.pizzaModels = new ArrayList<>();
        this.orderModels = new ArrayList<>();
        this.balance = 0.0;
        this.name = name;
        this.stockModel = stockModel;
    }

    public String getOrders() {
        int cont = 1;
        StringBuilder sOrders = new StringBuilder();

        for (OrderModel orderModel : this.orderModels)
            sOrders.append((cont++)).append(" - ").append(orderModel.toString());

        return sOrders.toString();
    }

    public void addOrder(OrderModel orderModel) {
        this.orderModels.add(orderModel);
    }

    public void incBalance(double balance) {
        this.balance += balance;
    }

    public void decBalance(double balance) {
        this.balance -= balance;
    }
}
