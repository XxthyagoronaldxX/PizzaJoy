package com.danthy.pizzafun.app.controllers.widgets.ordercell;


import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.events.RequestProduceOrderEvent;
import com.danthy.pizzafun.app.events.SuccessProduceOrderEvent;
import com.danthy.pizzafun.domain.models.OrderModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderCellListController extends IController {
    @FXML
    public ImageView orderImageBg;

    @FXML
    public VBox cellRoot;

    @FXML
    public Label itemsLabel;

    @FXML
    public Label pizzaNameLabel;

    @FXML
    public Button produceButton;

    @FXML
    public ProgressBar progressStatus;

    @FXML
    public ImageView pizzaLoadingBackgroundImg;

    public OrderWrapper orderWrapper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderImageBg.fitWidthProperty().bind(cellRoot.prefWidthProperty());
        orderImageBg.fitHeightProperty().bind(cellRoot.prefHeightProperty());
        cellRoot.setAlignment(Pos.CENTER);
    }

    public void setOrderWrapper(OrderWrapper orderWrapper) {
        this.orderWrapper = orderWrapper;
        OrderModel orderModel = orderWrapper.getOrderModel();

        orderWrapper.setProgressBar(progressStatus);
        orderWrapper.setButton(produceButton);

        produceButton.setVisible(!orderWrapper.isLoading());
        progressStatus.setVisible(orderWrapper.isLoading());
        pizzaLoadingBackgroundImg.setVisible(orderWrapper.isLoading());
        progressStatus.setProgress(orderWrapper.getProgress());
        produceButton.setOnMouseClicked(this::onRequestProduceOrderEvent);

        itemsLabel.setText(orderModel.toString());
        pizzaNameLabel.setText(orderModel.getPizzaModel().getName());
    }

    private void handleLoadingToProduce() {
        boolean isLoading = orderWrapper.isLoading();

        if (isLoading) {
            orderWrapper.getButton().setVisible(false);
            orderWrapper.getProgressBar().setVisible(true);
            pizzaLoadingBackgroundImg.setVisible(true);
            orderWrapper.setLoading(true);


            int totDurationSeconds = ApplicationProperties.pizzaProduceBaseTime;
            Timeline produceTimeline = new Timeline(new KeyFrame(Duration.seconds(1), timeEvent -> {
                double progress = orderWrapper.getProgress();

                if (progress < 1.0) {
                    orderWrapper.setProgress(progress + 1.0 / totDurationSeconds);

                    orderWrapper.getProgressBar().setProgress(orderWrapper.getProgress());
                }
            }));
            produceTimeline.setCycleCount(totDurationSeconds + 1);
            produceTimeline.setOnFinished(onFinish -> {
                eventPublisher.notifyAll(new SuccessProduceOrderEvent(orderWrapper));
            });
            produceTimeline.play();
        }
    }

    public void startToProduceOrder() {
        orderWrapper.setLoading(true);

        handleLoadingToProduce();
    }

    public void onRequestProduceOrderEvent(MouseEvent event) {
        eventPublisher.notifyAll(new RequestProduceOrderEvent(orderWrapper, this));
    }
}
