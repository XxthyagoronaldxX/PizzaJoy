package com.danthy.pizzafun.domain.models;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@XmlRootElement
public class StockModel {
    @EqualsAndHashCode.Include
    @XmlAttribute(name = "id")
    private UUID id;

    @XmlElementWrapper(name = "itemStockModelList")
    @XmlElement(name = "itemStockModel")
    private List<ItemStockModel> itemStockModels;

    @XmlElement
    private int currentWeight;

    @XmlElement
    private int totalWeight;
}
