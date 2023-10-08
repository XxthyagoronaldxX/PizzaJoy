package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapper;
import com.danthy.pizzafun.domain.models.SupplierModel;

public interface ISupplierWrapper extends IWrapper<SupplierModel> {
    void increaseRateSpeed(double rateSpeed);

    String getCostPrint();

    String getNamePrint();

    String getDeliveryTimeInSecondsPrint() ;

    String getBonusChancePrint();

    String getBuyTokenPrint();

    int getBuyToken();
}
