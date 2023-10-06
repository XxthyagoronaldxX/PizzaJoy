package com.danthy.pizzafun.domain.models;

import com.danthy.pizzafun.domain.enums.NpcLevel;

import java.util.UUID;

public class NpcModel {

    private UUID id;
    private String name;
    private NpcLevel level;

    public NpcModel(String name, NpcLevel level) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.level = level;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NpcLevel getLevel() {
        return level;
    }

    public void setLevel(NpcLevel level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        NpcModel other = (NpcModel) obj;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        return true;
    }
}
