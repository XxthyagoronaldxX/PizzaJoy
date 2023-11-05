package com.danthy.pizzafun.domain.models;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@XmlRootElement
public class ItemStockModel {
    @EqualsAndHashCode.Include
    @XmlAttribute
    private UUID id;

    @XmlElement
    private ItemModel itemModel;

    @XmlElement
    private boolean isLocked;

    @XmlElement
    private int quantity;

    public ItemStockModel() {
        this.id = UUID.randomUUID();
    }

    public ItemStockModel(ItemModel itemModel) {
        this.id = UUID.randomUUID();
        this.itemModel = itemModel;
        this.quantity = 0;
    }

    public void incrementQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decrementQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
