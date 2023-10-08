package com.danthy.pizzafun.app.wrappers.implementations;

import com.danthy.pizzafun.app.wrappers.ISupplierWrapper;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SupplierWrapper implements ISupplierWrapper {
    private final SupplierModel supplierModel;

    public double rateSpeed = 1.0;

    public SupplierWrapper(SupplierModel supplierModel) {
        this.supplierModel = supplierModel;
    }

    @Override
    public void increaseRateSpeed(double rateSpeed) {
        this.rateSpeed += rateSpeed;

        Timeline removeBurnTimeline = new Timeline(new KeyFrame(Duration.seconds(1.2)));
        removeBurnTimeline.setOnFinished((event) -> this.rateSpeed -= rateSpeed);
        removeBurnTimeline.play();
    }

    @Override
    public String getCostPrint() {
        return String.format("Custo: R$ %.2f", this.supplierModel.getCost());
    }

    @Override
    public String getNamePrint() {
        return this.supplierModel.getName();
    }

    @Override
    public String getDeliveryTimeInSecondsPrint() {
        return String.format("Velocidade: %.2fs", this.supplierModel.getDeliveryTimeInSeconds());
    }

    @Override
    public String getBonusChancePrint() {
        return String.format("Bonus Chance: %.2f%s", this.supplierModel.getBonusChance() * 100, "%");
    }

    @Override
    public String getBuyTokenPrint() {
        return String.format("%d TK", this.supplierModel.getBuyToken());
    }

    @Override
    public int getBuyToken() {
        return supplierModel.getBuyToken();
    }

    @Override
    public SupplierModel getWrappe() {
        return this.supplierModel;
    }
}
