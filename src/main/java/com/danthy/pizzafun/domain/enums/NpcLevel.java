package com.danthy.pizzafun.domain.enums;

public enum NpcLevel {
    EASY(1),
    REGULAR(2),
    HARD(3);

    private final int level;

    private NpcLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
}
