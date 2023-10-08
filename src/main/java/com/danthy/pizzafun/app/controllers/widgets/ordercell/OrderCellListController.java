package com.danthy.pizzafun.app.controllers.widgets.ordercell;


import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import com.danthy.pizzafun.app.wrappers.implementations.OrderWrapper;
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

    private final OrderWrapper orderModelWrapper;

    public OrderCellListController(OrderWrapper orderModelWrapper) {
        this.orderModelWrapper = orderModelWrapper;

        FxmlUtil.loadFXMLInjectController(this, PathFxmlUtil.ORDER_CELL_LIST_WIDGET);
        initialize();
    }

    private void initialize() {
        OrderModel orderModel = orderModelWrapper.getOrderModel();

        orderModelWrapper.setProgressBar(progressStatus);

        produceButton.setVisible(!orderModelWrapper.isLoading());
        progressStatus.setVisible(orderModelWrapper.isLoading());
        progressStatus.setProgress(orderModelWrapper.getProgress());

        itemsLabel.setText(orderModel.toString());
        pizzaNameLabel.setText(orderModel.getPizzaModel().getName());
        produceButton.setOnMouseClicked(this::produceOrderEvent);

        if (!orderModelWrapper.isAlreadyAnimated()) animate();

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
        orderModelWrapper.setAlreadyAnimated(true);
    }

    @FXML
    private void produceOrderEvent(MouseEvent event) {
        if (orderModelWrapper.produceOrder()) {
            produceButton.setVisible(false);
            progressStatus.setVisible(true);
        }
    }
}
