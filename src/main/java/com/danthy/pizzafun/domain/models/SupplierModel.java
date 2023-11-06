package com.danthy.pizzafun.domain.models;

import com.danthy.pizzafun.domain.enums.SupplierLevel;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SupplierModel {
    @EqualsAndHashCode.Include
    @XmlAttribute(name = "id")
    private UUID id;

    @XmlElement
    private SupplierLevel supplierLevel;

    @XmlElement
    private String name;

    @XmlElement
    private int bonus;

    @XmlElement
    private int buyToken;

    @XmlElement
    private double bonusChance;

    @XmlElement
    private double deliveryTimeInSeconds;

    public SupplierModel(String name, SupplierLevel supplierLevel) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.supplierLevel = supplierLevel;

        this.bonus = supplierLevel.getBonus();
        this.bonusChance = supplierLevel.getBonusChance();
        this.deliveryTimeInSeconds = supplierLevel.getDeliveryTimeInSeconds();
        this.buyToken = supplierLevel.getBuyToken();
    }
}
