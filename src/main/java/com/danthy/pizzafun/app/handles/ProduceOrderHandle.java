package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.contracts.IHandle;
import com.danthy.pizzafun.app.events.ProducedOrderEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderWrapper;
import com.danthy.pizzafun.domain.models.OrderModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ProduceOrderHandle implements IHandle {
    private final OrderWrapper orderWrapper;
    private final StockServiceImpl stockService;

    public ProduceOrderHandle(OrderWrapper orderWrapper, StockServiceImpl stockService) {
        this.orderWrapper = orderWrapper;
        this.stockService = stockService;
    }

    @Override
    public void handle() {
        OrderModel orderModel = orderWrapper.getOrderModel();
        boolean isLoading = orderWrapper.isLoading();
        boolean isItemStockAlreadyRemoved = orderWrapper.isItemStockAlreadyRemoved();

        if (stockService.isRemoveOrderValid(orderModel) || isLoading) {
            orderWrapper.getButton().setVisible(false);
            orderWrapper.getProgressBar().setVisible(true);

            if (!isItemStockAlreadyRemoved) {
                stockService.removeItemStockFromOrder(orderModel);
                orderWrapper.setItemStockAlreadyRemoved(true);
            }

            orderWrapper.setLoading(true);

            int totDurationSeconds = ApplicationProperties.pizzaProduceBaseTime;
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), timeEvent -> {
                double progress = orderWrapper.getProgress();

                if (progress < 1.0) {
                    orderWrapper.setProgress(progress + 1.0 / totDurationSeconds);

                    orderWrapper.getProgressBar().setProgress(orderWrapper.getProgress());
                }
            }));


            timeline.setCycleCount(totDurationSeconds);
            timeline.setOnFinished(onFinish -> {
                GetIt.getInstance().find(EventPublisher.class).notifyAll(new ProducedOrderEvent(orderWrapper));
            });
            timeline.play();
        }
    }
}
