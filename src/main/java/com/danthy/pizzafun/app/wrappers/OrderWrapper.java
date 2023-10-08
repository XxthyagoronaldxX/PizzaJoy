package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapperModel;
import com.danthy.pizzafun.app.events.ProducedOrderEvent;
import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.domain.models.OrderModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderWrapper implements IWrapperModel<OrderModel> {
    private OrderModel orderModel;

    private Double progress;

    private ProgressBar progressBar;

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

    @Override
    public OrderModel getWrapped() {
        return this.orderModel;
    }
}
