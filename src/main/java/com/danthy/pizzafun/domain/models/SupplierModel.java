package com.danthy.pizzafun.domain.models;

import com.danthy.pizzafun.domain.enums.SupplierLevel;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SupplierModel {
    @EqualsAndHashCode.Include
    private UUID id;

    private final SupplierLevel supplierLevel;

    private String name;

    private double cost;

    private int bonus;

    private int buyToken;

    private double bonusChance;

    private double deliveryTimeInSeconds;

    public SupplierModel(String name, SupplierLevel supplierLevel) {
        this.name = name;
        this.supplierLevel = supplierLevel;

        this.cost = supplierLevel.getCost();
        this.bonus = supplierLevel.getBonus();
        this.bonusChance = supplierLevel.getBonusChance();
        this.deliveryTimeInSeconds = supplierLevel.getDeliveryTimeInSeconds();
        this.buyToken = supplierLevel.getBuyToken();
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
