package com.danthy.pizzafun.domain.models;

import com.danthy.pizzafun.domain.enums.UpgradeType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
    private int tokenUpgradeCost;

    @XmlElement
    private int tokenUpgradeCostBase;

    @XmlElement
    private int tokenUpgradeCostScale;

    @XmlElement
    private UpgradeType upgradeType;

    public UpgradeModel() {
        this.id = UUID.randomUUID();
    }

    public UpgradeModel getClone() {
        UpgradeModel upgradeModelClone = new UpgradeModel();

        upgradeModelClone.setUpgradeType(upgradeType);
        upgradeModelClone.setName(name);
        upgradeModelClone.setLevel(level);
        upgradeModelClone.setTokenUpgradeCost(tokenUpgradeCost);
        upgradeModelClone.setTokenUpgradeCostBase(tokenUpgradeCostScale);
        upgradeModelClone.setTokenUpgradeCostScale(tokenUpgradeCostScale);
        upgradeModelClone.setUpgradeCost(upgradeCost);
        upgradeModelClone.setUpgradeCostBase(upgradeCostBase);
        upgradeModelClone.setUpgradeCostScale(upgradeCostScale);

        return upgradeModelClone;
    }

    public void upgrade() {
        this.upgradeCost += this.upgradeCostBase * this.upgradeCostScale;
        this.tokenUpgradeCost += this.tokenUpgradeCostBase * this.tokenUpgradeCostScale;

        this.level += 1;
    }
}
