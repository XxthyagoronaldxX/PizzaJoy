package com.danthy.pizzafun.domain.models;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@XmlRootElement
public class PizzaEdgeModel {
    @EqualsAndHashCode.Include
    @XmlAttribute(name = "id")
    private String name;

    @XmlElementWrapper(name = "itemPizzaModelList")
    @XmlElement(name = "itemPizzaModel")
    private List<ItemPizzaModel> itemPizzaModels;
}
