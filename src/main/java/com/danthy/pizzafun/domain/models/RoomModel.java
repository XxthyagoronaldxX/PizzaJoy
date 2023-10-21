package com.danthy.pizzafun.domain.models;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@XmlRootElement
public class RoomModel implements Cloneable {
    @EqualsAndHashCode.Include
    @XmlAttribute
    private UUID id;

    @XmlElement
    private String name;

    @XmlElement
    private double balance;

    @XmlElement
    private int tokens;

    @XmlElement
    private int totalOrderAmount;

    @XmlElement
    private StockModel stockModel;

    @XmlElement
    private SupplierModel supplierModel;

    @XmlElementWrapper(name = "pizzaModelList")
    @XmlElement(name = "pizzaModel")
    private List<PizzaModel> pizzaModels;

    @XmlElementWrapper(name = "upgradeModelList")
    @XmlElement(name = "upgradeModel")
    private List<UpgradeModel> upgradeModelList;

    @XmlElementWrapper(name = "orderModelList")
    @XmlElement(name = "orderModel")
    private List<OrderModel> orderModels;

    @Override
    public RoomModel clone() {
        try {
            RoomModel clone = (RoomModel) super.clone();

            BeanUtils.copyProperties(this, clone);

            return clone;
        } catch (CloneNotSupportedException | IllegalAccessException | InvocationTargetException exception) {
            throw new RuntimeException(exception);
        }
    }
}
