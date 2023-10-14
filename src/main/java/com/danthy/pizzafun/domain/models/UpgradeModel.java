package com.danthy.pizzafun.domain.models;

import com.danthy.pizzafun.domain.enums.UpgradeType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor
public class UpgradeModel {
    @EqualsAndHashCode.Include
    @XmlAttribute(name = "id")
    private UUID id;

    @XmlElement
    private int level;

    @XmlElement
    private String name;

    @XmlElement
    private double upgradeCost;

    @XmlElement
    private double upgradeCostBase;

    @XmlElement
    private double upgradeCostScale;

    @XmlElement
    private UpgradeType upgradeType;

    public UpgradeModel getClone() {
        UpgradeModel upgradeModelClone = new UpgradeModel();

        upgradeModelClone.setLevel(level);
        upgradeModelClone.setName(name);
        upgradeModelClone.setUpgradeCost(upgradeCost);
        upgradeModelClone.setUpgradeCostScale(upgradeCostScale);
        upgradeModelClone.setUpgradeCostBase(upgradeCostBase);
        upgradeModelClone.setUpgradeType(upgradeType);

        return upgradeModelClone;
    }

    public void upgrade() {
        this.upgradeCost += this.upgradeCostBase * this.upgradeCostScale;

        this.level += 1;
    }

    public void downgrade() {
        this.upgradeCost -= this.upgradeCostBase * this.upgradeCostScale;

        this.level -= 1;
    }
}
