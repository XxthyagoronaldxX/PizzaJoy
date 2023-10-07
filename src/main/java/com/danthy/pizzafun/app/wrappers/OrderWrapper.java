package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.events.ProducedOrderEvent;
import com.danthy.pizzafun.app.utils.ApplicationProperties;
import com.danthy.pizzafun.app.utils.EventPublisher;
import com.danthy.pizzafun.app.utils.GetIt;
import com.danthy.pizzafun.domain.models.OrderModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class OrderWrapper {
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

    public boolean produceOrder() {
        RoomWrapper roomWrapper = GetIt.getInstance().find(RoomWrapper.class);

        if (roomWrapper.isRemoveOrderValid(orderModel) || isLoading) {
            if (!this.isItemStockAlreadyRemoved) {
                roomWrapper.removeItemStockFromOrder(orderModel);
                this.isItemStockAlreadyRemoved = true;
            }

            this.isLoading = true;

            Platform.runLater(() -> {
                int totDurationSeconds = ApplicationProperties.pizzaProduceBaseTime;
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), timeEvent -> {
                    double progress = getProgress();
                    if (progress < 1.0) {
                        setProgress(progress + 1.0 / totDurationSeconds);

                        progressBar.setProgress(getProgress());
                    }
                }));


                timeline.setCycleCount(totDurationSeconds);
                timeline.setOnFinished(onFinish -> {
                    GetIt.getInstance().find(EventPublisher.class).notifyAll(new ProducedOrderEvent(this));
                });
                timeline.play();
            });

            return true;
        }

        return false;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public boolean isAlreadyAnimated() {
        return isAlreadyAnimated;
    }

    public void setAlreadyAnimated(boolean alreadyAnimated) {
        isAlreadyAnimated = alreadyAnimated;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
