package com.danthy.pizzafun.domain.models;

import com.danthy.pizzafun.domain.enums.NpcLevel;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@XmlRootElement
public class NpcModel {
    @EqualsAndHashCode.Include
    @XmlAttribute
    private UUID id;

    @XmlElement
    private String name;

    @XmlElement
    private NpcLevel level;

    public NpcModel(String name, NpcLevel level) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.level = level;
    }
}
