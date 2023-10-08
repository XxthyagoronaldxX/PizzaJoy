package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.domain.models.SupplierModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SupplierWrapper {
    private final SupplierModel supplierModel;

    public String getCostPrint() {
        return String.format("Custo: R$ %.2f", this.supplierModel.getCost());
    }

    public String getNamePrint() {
        return this.supplierModel.getName();
    }

    public String getDeliveryTimeInSecondsPrint() {
        return String.format("Velocidade: %.2fs", this.supplierModel.getDeliveryTimeInSeconds());
    }

    public String getBonusChancePrint() {
        return String.format("Bonus Chance: %.2f%s", this.supplierModel.getBonusChance() * 100, "%");
    }

    public String getBuyTokenPrint() {
        return String.format("%d TK", this.supplierModel.getBuyToken());
    }

    public int getBuyToken() {
        return supplierModel.getBuyToken();
    }
}
