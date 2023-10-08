package com.danthy.pizzafun.app.services;

public interface ISupplierWrapperService {
    void increaseRateSpeed(double rateSpeed);

    String getCostPrint();

    String getNamePrint();

    String getDeliveryTimeInSecondsPrint() ;

    String getBonusChancePrint();

    String getBuyTokenPrint();

    int getBuyToken();
}
