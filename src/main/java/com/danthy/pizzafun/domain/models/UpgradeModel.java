package com.danthy.pizzafun.domain.models;

import com.danthy.pizzafun.domain.enums.UpgradeType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class UpgradeModel {
    @EqualsAndHashCode.Include
    private UUID id;
    private int level;
    private String name;
    private double upgradeCost;
    private double upgradeCostBase;
    private double upgradeCostScale;
    private UpgradeType upgradeType;

    public UpgradeModel() {
        this.id = UUID.randomUUID();
    }

    public UpgradeModel getClone() {
        return UpgradeModel.build()
                .setLevel(level)
                .setName(name)
                .setUpgradeCost(upgradeCost)
                .setUpgradeCostScale(upgradeCostScale)
                .setUpgradeCostBase(upgradeCostBase);
    }

    public void upgrade() {
        this.level += 1;
    }

    public void downgrade() {
        this.level -= 1;
    }

    public static UpgradeModel build() {
        return new UpgradeModel();
    }

    public UpgradeModel setName(String name) {
        this.name = name;

        return this;
    }

    public UpgradeModel setLevel(int level) {
        this.level = level;

        return this;
    }

    public UpgradeModel setUpgradeCostBase(double upgradeCostBase) {
        this.upgradeCostScale = upgradeCostBase;

        return this;
    }

    public UpgradeModel setUpgradeCost(double upgradeCost) {
        this.upgradeCost = upgradeCost;

        return this;
    }

    public UpgradeModel setUpgradeCostScale(double upgradeCostScale) {
        this.upgradeCostScale = upgradeCostScale;

        return this;
    }
}
