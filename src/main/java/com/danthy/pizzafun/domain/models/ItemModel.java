package com.danthy.pizzafun.domain.models;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@XmlRootElement
public class ItemModel {
    @EqualsAndHashCode.Include
    @XmlAttribute(name = "id")
    private String name;

    @XmlAttribute
    private int weight;
}
