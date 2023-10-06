package com.danthy.pizzafun.domain.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RoomModel {

    public static RoomModel I;
    private UUID id;
    private String name;
    private double balance;
    private StockModel stockModel;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StockModel getStockModel() {
        return stockModel;
    }

    public void setStockModel(StockModel stockModel) {
        this.stockModel = stockModel;
    }

    public List<PizzaModel> getPizzaModels() {
        return pizzaModels;
    }

    public void addPizza(PizzaModel pizzaModel) {
        this.pizzaModels.add(pizzaModel);
    }

    public void setPizzaModels(List<PizzaModel> pizzaModels) {
        this.pizzaModels = pizzaModels;
    }

    public String getOrders() {
        int cont = 1;
        StringBuilder sOrders = new StringBuilder();

        for (OrderModel orderModel : this.orderModels)
            sOrders.append((cont++)).append(" - ").append(orderModel.toString());

        return sOrders.toString();
    }

    public List<OrderModel> getOrderModels() {
        return orderModels;
    }

    public void addOrder(OrderModel orderModel) {
        this.orderModels.add(orderModel);
    }

    public double getBalance() {
        return balance;
    }

    public void incBalance(double balance) {
        this.balance += balance;
    }

    public void decBalance(double balance) {
        this.balance -= balance;
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RoomModel other = (RoomModel) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }
}
