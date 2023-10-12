package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.contracts.IHandle;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderCellListController;
import com.danthy.pizzafun.app.events.ProducedOrderEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderWrapper;
import com.danthy.pizzafun.domain.models.OrderModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class OnProduceOrderEvent implements EventHandler<MouseEvent> {
    private final OrderWrapper orderWrapper;
    private final StockServiceImpl stockService;
    private final EventPublisher eventPublisher;
    private final ImageView pizzaLoadingBackgroundImg;

    public OnProduceOrderEvent(OrderCellListController controller) {
        orderWrapper = controller.orderWrapper;
        pizzaLoadingBackgroundImg = controller.pizzaLoadingBackgroundImg;

        stockService = GetIt.getInstance().find(StockServiceImpl.class);
        eventPublisher = GetIt.getInstance().find(EventPublisher.class);
    }

    @Override
    public void handle(MouseEvent event) {
        OrderModel orderModel = orderWrapper.getOrderModel();
        boolean isLoading = orderWrapper.isLoading();
        boolean isItemStockAlreadyRemoved = orderWrapper.isItemStockAlreadyRemoved();

        if (stockService.isRemoveOrderValid(orderModel) || isLoading) {
            orderWrapper.getButton().setVisible(false);
            orderWrapper.getProgressBar().setVisible(true);
            pizzaLoadingBackgroundImg.setVisible(true);


            if (!isItemStockAlreadyRemoved) {
                stockService.removeItemStockFromOrder(orderModel);
                orderWrapper.setItemStockAlreadyRemoved(true);
            }

            orderWrapper.setLoading(true);


            int totDurationSeconds = ApplicationProperties.pizzaProduceBaseTime;
            Timeline produceTimeline = new Timeline(new KeyFrame(Duration.seconds(1), timeEvent -> {
                double progress = orderWrapper.getProgress();

                if (progress < 1.0) {
                    orderWrapper.setProgress(progress + 1.0 / totDurationSeconds);

                    orderWrapper.getProgressBar().setProgress(orderWrapper.getProgress());
                }
            }));
            produceTimeline.setCycleCount(totDurationSeconds);
            produceTimeline.setOnFinished(onFinish -> {
                eventPublisher.notifyAll(new ProducedOrderEvent(orderWrapper));
            });
            produceTimeline.play();
        }
    }
}
