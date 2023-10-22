package com.danthy.pizzafun.app.controllers.pizzaria.widgets.ordercell;

import com.danthy.pizzafun.domain.models.OrderModel;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderWrapper {
    private OrderModel orderModel;

    private Double progress;

    private ProgressBar progressBar;

    private Button button;

    private boolean isLoading;

    private boolean isItemStockAlreadyRemoved;

    private boolean isAlreadyAnimated;

    public OrderWrapper(OrderModel orderModel) {
        this.orderModel = orderModel;
        this.progress = 0.0;
        this.isLoading = false;
        this.isAlreadyAnimated = false;
        this.isItemStockAlreadyRemoved = false;
    }
}
