package com.danthy.pizzafun.domain.enums;

public enum SupplierLevel {
    LEVEL01(20.0, 40.0, 45, 60, 0, 0.1, 10, 30),
    LEVEL02(25.0, 50.0, 40, 55, 0.1, 0.225, 25, 50),
    LEVEL03(29.5, 75.0, 35, 50, 0.225, 0.325, 40, 80);

    private final double minCost;
    private final double maxCost;
    private final double minDeliveryTimeInSeconds;
    private final double maxDeliveryTimeInSeconds;
    private final double minBonusChance;
    private final double maxBonusChance;
    private final int minBonus;
    private final int maxBonus;

    SupplierLevel(double minCost, double maxCost, double minDeliveryTimeInSeconds, double maxDeliveryTimeInSeconds, double minBonusChance, double maxBonusChance, int minBonus, int maxBonus) {
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.minDeliveryTimeInSeconds = minDeliveryTimeInSeconds;
        this.maxDeliveryTimeInSeconds = maxDeliveryTimeInSeconds;
        this.minBonusChance = minBonusChance;
        this.maxBonusChance = maxBonusChance;
        this.minBonus = minBonus;
        this.maxBonus = maxBonus;
    }

    public double getCost() {
        return (Math.random() * (maxCost - minCost)) + minCost;
    }

    public double getDeliveryTimeInSeconds() {
        return (Math.random() * (maxDeliveryTimeInSeconds - minDeliveryTimeInSeconds)) + minDeliveryTimeInSeconds;
    }

    public double getBonusChance() {
        return (Math.random() * (maxBonusChance - minBonusChance)) + minBonusChance;
    }

    public int getBonus() {
        return ((int) Math.ceil(Math.random() * (maxBonus - minBonus))) + minBonus;
    }
}
