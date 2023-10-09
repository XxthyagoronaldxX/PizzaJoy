package com.danthy.pizzafun.app.controllers.widgets.ordercell;


import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.handles.ProduceOrderHandle;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import com.danthy.pizzafun.domain.models.OrderModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderCellListController implements IController  {
    @FXML
    public ImageView orderImageBg;

    @FXML
    private VBox cellRoot;

    @FXML
    private Label itemsLabel;

    @FXML
    private Label pizzaNameLabel;

    @FXML
    private Button produceButton;

    @FXML
    private ProgressBar progressStatus;

    private OrderWrapper orderWrapper;

    public OrderCellListController() {
    }

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
        progressStatus.setProgress(orderWrapper.getProgress());
        produceButton.setOnMouseClicked(this::produceOrderEvent);

        itemsLabel.setText(orderModel.toString());
        pizzaNameLabel.setText(orderModel.getPizzaModel().getName());

        // if (!orderWrapper.isAlreadyAnimated()) {
        //    this.playEntryAnimation();
        //    orderWrapper.setAlreadyAnimated(true);
        //}
    }

    @FXML
    private void produceOrderEvent(MouseEvent event) {
        new ProduceOrderHandle(orderWrapper, GetIt.getInstance().find(StockServiceImpl.class)).handle();
    }
}
