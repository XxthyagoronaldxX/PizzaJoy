package com.danthy.pizzafun.domain.models;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@XmlRootElement
public class ItemPizzaModel {
    @XmlElement
    private ItemModel itemModel;

    @XmlElement
    private int quantity;
}
