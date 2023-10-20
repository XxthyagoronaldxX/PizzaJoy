package com.danthy.pizzafun.app.controllers.widgets.ordercell;


import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.events.mediator.RequestProduceOrderEvent;
import com.danthy.pizzafun.app.events.mediator.SuccessProduceOrderEvent;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IUpgradeService;
import com.danthy.pizzafun.app.services.implementations.UpgradeServiceImpl;
import com.danthy.pizzafun.domain.enums.UpgradeType;
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

public class OrderCellListController implements IController, IMediatorEmitter {
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

    public IUpgradeService upgradeService;

    public OrderWrapper orderWrapper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderImageBg.fitWidthProperty().bind(cellRoot.prefWidthProperty());
        orderImageBg.fitHeightProperty().bind(cellRoot.prefHeightProperty());
        cellRoot.setAlignment(Pos.CENTER);
    }

    public void setOrderWrapper(OrderWrapper orderWrapper) {
        this.upgradeService = GetIt.getInstance().find(UpgradeServiceImpl.class);
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

            int cookLevel = upgradeService.getLevel(UpgradeType.COOK);
            int timeInSecondsToProduce = orderWrapper.getOrderModel().getPizzaModel().getTimeInSecondsToProduce();
            double produceBaseLevelUp = ApplicationProperties.pizzaProduceBaseLevelUp;
            double totDurationSeconds = timeInSecondsToProduce - (cookLevel * produceBaseLevelUp);

            KeyFrame produceKeyFrame = new KeyFrame(Duration.seconds(1), timeEvent -> {
                double progress = orderWrapper.getProgress();

                if (progress < 1.0) {
                    orderWrapper.setProgress(progress + 1.0 / totDurationSeconds);

                    orderWrapper.getProgressBar().setProgress(orderWrapper.getProgress());
                }
            });

            Timeline produceTimeline = new Timeline(produceKeyFrame);
            produceTimeline.setCycleCount((int) totDurationSeconds + 1);
            produceTimeline.setOnFinished(onFinish -> {
                this.sendEvent(new SuccessProduceOrderEvent(orderWrapper));
            });
            produceTimeline.play();
        }
    }

    public void startToProduceOrder() {
        orderWrapper.setLoading(true);

        handleLoadingToProduce();
    }

    public void onRequestProduceOrderEvent(MouseEvent event) {
        this.sendEvent(new RequestProduceOrderEvent(orderWrapper, this));
    }
}
