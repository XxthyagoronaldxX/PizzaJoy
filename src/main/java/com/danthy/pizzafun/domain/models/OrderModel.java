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
@NoArgsConstructor
@XmlRootElement
public class OrderModel {
    @EqualsAndHashCode.Include
    @XmlAttribute
    private UUID id;

    @XmlElement
    private NpcModel npcModel;

    @XmlElement
    private PizzaModel pizzaModel;

    public OrderModel(NpcModel npcModel, PizzaModel pizzaModel) {
        this.id = UUID.randomUUID();
        this.npcModel = npcModel;
        this.pizzaModel = pizzaModel;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (ItemPizzaModel itemPizzaModel : pizzaModel.getItemPizzaModels())
            stringBuilder
                    .append(itemPizzaModel.getQuantity())
                    .append("x ")
                    .append(itemPizzaModel.getItemModel().getName())
                    .append("\n");

        return stringBuilder.toString();
    }
}
