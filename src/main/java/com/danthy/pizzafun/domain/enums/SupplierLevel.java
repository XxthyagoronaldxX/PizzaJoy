package com.danthy.pizzafun.domain.enums;

public enum SupplierLevel {
    LEVEL01(45, 60, 0, 0.1, 10, 30, 10, 15),
    LEVEL02(40, 55, 0.1, 0.225, 25, 50, 25, 50),
    LEVEL03( 35, 50, 0.225, 0.325, 40, 80, 70, 120);

    private final double minDeliveryTimeInSeconds;
    private final double maxDeliveryTimeInSeconds;
    private final double minBonusChance;
    private final double maxBonusChance;
    private final int minBonus;
    private final int maxBonus;
    private final int minBuyToken;
    private final int maxBuyToken;

    SupplierLevel(double minDeliveryTimeInSeconds, double maxDeliveryTimeInSeconds, double minBonusChance, double maxBonusChance, int minBonus, int maxBonus, int minBuyToken, int maxBuyToken) {
        this.minDeliveryTimeInSeconds = minDeliveryTimeInSeconds;
        this.maxDeliveryTimeInSeconds = maxDeliveryTimeInSeconds;
        this.minBonusChance = minBonusChance;
        this.maxBonusChance = maxBonusChance;
        this.minBonus = minBonus;
        this.maxBonus = maxBonus;
        this.minBuyToken = minBuyToken;
        this.maxBuyToken = maxBuyToken;
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

    public int getBuyToken() {return ((int) Math.ceil(Math.random() * (maxBuyToken - minBuyToken))) + minBuyToken;}
}
