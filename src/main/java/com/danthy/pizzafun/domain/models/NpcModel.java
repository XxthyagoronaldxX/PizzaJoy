package com.danthy.pizzafun.domain.models;

import com.danthy.pizzafun.domain.enums.NpcLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NpcModel {
    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    private NpcLevel level;

    public NpcModel(String name, NpcLevel level) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.level = level;
    }
}
