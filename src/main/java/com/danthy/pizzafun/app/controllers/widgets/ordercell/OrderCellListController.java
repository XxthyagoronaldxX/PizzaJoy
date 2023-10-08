package com.danthy.pizzafun.app.controllers.widgets.ordercell;


import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import com.danthy.pizzafun.domain.models.OrderModel;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class OrderCellListController extends AnchorPane {
    @FXML
    private Label itemsLabel;

    @FXML
    private Label pizzaNameLabel;

    @FXML
    private AnchorPane cellRoot;

    @FXML
    private Button produceButton;

    @FXML
    private ProgressBar progressStatus;

    private final OrderCellListModel orderCellListModel;

    public OrderCellListController(OrderCellListModel orderCellListModel) {
        this.orderCellListModel = orderCellListModel;

        FxmlUtil.loadFXMLInjectController(this, PathFxmlUtil.ORDER_CELL_LIST_WIDGET);
        initialize();
    }

    private void initialize() {
        OrderWrapper orderWrapper = orderCellListModel.getOrderWrapper();
        OrderModel orderModel = orderWrapper.getOrderModel();

        orderWrapper.setProgressBar(progressStatus);

        produceButton.setVisible(!orderWrapper.isLoading());
        progressStatus.setVisible(orderWrapper.isLoading());
        progressStatus.setProgress(orderWrapper.getProgress());

        itemsLabel.setText(orderModel.toString());
        pizzaNameLabel.setText(orderModel.getPizzaModel().getName());
        produceButton.setOnMouseClicked(this::produceOrderEvent);

        if (!orderWrapper.isAlreadyAnimated()) animate();

        getChildren().add(cellRoot);
        setBottomAnchor(cellRoot, 0.0);
        setTopAnchor(cellRoot, 0.0);
    }

    private void animate() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.2), this);
        translateTransition.setFromY((-1) * 300);
        translateTransition.setToY(0);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.2), this);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);
        parallelTransition.play();
        orderCellListModel.getOrderWrapper().setAlreadyAnimated(true);
    }

    @FXML
    private void produceOrderEvent(MouseEvent event) {
        if (orderCellListModel.produceOrder()) {
            produceButton.setVisible(false);
            progressStatus.setVisible(true);
        }
    }
}
