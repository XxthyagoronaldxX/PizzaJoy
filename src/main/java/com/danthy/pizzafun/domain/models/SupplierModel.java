package com.danthy.pizzafun.domain.models;

import com.danthy.pizzafun.domain.enums.SupplierLevel;

public class SupplierModel {
    private final SupplierLevel supplierLevel;

    private String name;

    private double cost;

    private int bonus;

    private double bonusChance;

    private double deliveryTimeInSeconds;

    public SupplierModel(String name, SupplierLevel supplierLevel) {
        this.name = name;
        this.supplierLevel = supplierLevel;

        this.cost = supplierLevel.getCost();
        this.bonus = supplierLevel.getBonus();
        this.bonusChance = supplierLevel.getBonusChance();
        this.deliveryTimeInSeconds = supplierLevel.getDeliveryTimeInSeconds();
    }

    public SupplierModel(SupplierLevel supplierLevel, String name, double cost, int bonus, double bonusChance, double deliveryTimeInSeconds) {
        this.supplierLevel = supplierLevel;
        this.name = name;
        this.cost = cost;
        this.bonus = bonus;
        this.bonusChance = bonusChance;
        this.deliveryTimeInSeconds = deliveryTimeInSeconds;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public SupplierLevel getSupplierLevel() {
        return supplierLevel;
    }


    public double getBonusChance() {
        return bonusChance;
    }

    public void setBonusChance(double bonusChance) {
        this.bonusChance = bonusChance;
    }

    public double getDeliveryTimeInSeconds() {
        return deliveryTimeInSeconds;
    }

    public void setDeliveryTimeInSeconds(double deliveryTimeInSeconds) {
        this.deliveryTimeInSeconds = deliveryTimeInSeconds;
    }

    @Override
    public String toString() {
        return "SupplierModel{" +
                "supplierLevel=" + supplierLevel +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", bonus=" + bonus +
                ", bonusChance=" + bonusChance +
                ", deliveryTimeInSeconds=" + deliveryTimeInSeconds +
                '}';
    }
}
