package com.danthy.pizzafun.domain.models;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@XmlRootElement
public class PizzaModel {
    @EqualsAndHashCode.Include
    @XmlAttribute(name = "id")
    private String name;

    @XmlElementWrapper(name = "itemPizzaModelList")
    @XmlElement(name = "itemPizzaModel")
    private List<ItemPizzaModel> itemPizzaModels;

    @XmlElement
    private float priceToSell;

    @XmlElement
    private float priceToBuyRecipe;

    @XmlElement
    private float priceToGetRecipe;

    @XmlElement
    private int timeInSecondsToLearn;

    @XmlElement
    private int timeInSecondsToProduce;

    @XmlElement
    private PizzaEdgeModel pizzaEdgeModel;
}
