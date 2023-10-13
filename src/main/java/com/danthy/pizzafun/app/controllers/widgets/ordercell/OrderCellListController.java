package com.danthy.pizzafun.app.controllers.widgets.ordercell;


import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.handles.OnProduceOrderEvent;
import com.danthy.pizzafun.domain.models.OrderModel;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
        produceButton.setOnMouseClicked(new OnProduceOrderEvent(this));

        itemsLabel.setText(orderModel.toString());
        pizzaNameLabel.setText(orderModel.getPizzaModel().getName());
    }
}
