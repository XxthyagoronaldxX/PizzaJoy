package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEmitter;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderCellListFactory;
import com.danthy.pizzafun.app.events.*;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.wrappers.implementations.OrderWrapper;
import com.danthy.pizzafun.app.wrappers.implementations.RoomWrapper;
import com.danthy.pizzafun.app.wrappers.implementations.SupplierWrapper;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class RoomController extends IEmitter implements IListener, IController {
    @FXML
    public ListView ordersList;

    @FXML
    public Label balanceLabel;

    @FXML
    public AnchorPane roomView;

    @FXML
    public ImageView roomImageBg;

    @FXML
    public Label tokensLabel;

    @FXML
    public HBox rootView;

    @FXML
    public TokenShopController tokenShopController;

    @FXML
    public HBox tokenShop;

    @FXML
    public StockController stockController;

    private double maxWidth;

    private RoomWrapper roomWrapper;

    @Override
    public void initialize() {
        initUpgradeView();
        initOrderListView();
    }

    private void initUpgradeView() {
        roomView.widthProperty().addListener((observable, oldValue, newValue) -> {
            tokenShopController.tokenShopViewSubRoot.setPrefWidth(newValue.doubleValue());
        });

        tokenShop.toBack();
    }

    private void initOrderListView() {
        ordersList.setCellFactory(object -> new OrderCellListFactory());
        ordersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void openTokenShopViewEvent(MouseEvent mouseEvent) {
        tokenShop.toFront();
    }

    @FXML
    public void stockViewEvent(MouseEvent mouseEvent) {
        if (stockController.stockView.getPrefWidth() != stockController.prefWidthStockView) {
            openStockViewEvent();
        } else {
            closeStockViewEvent();
        }
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == ProducedOrderEvent.class) {
            onProducedOrderEvent(event);
        } else if (event.getClass() == StartGameEvent.class) {
            onStartGameEvent(event);
        } else if (event.getClass() == OrderGenerateEvent.class) {
            onOrderGenerateEvent(event);
        } else if (event.getClass() == ReStockEvent.class) {
            onRestockEvent(event);
        } else if (event.getClass() == SetSupplierEvent.class) {
            onSetSupplierEvent(event);
        }
    }

    public void onProducedOrderEvent(IEvent event) {
        ProducedOrderEvent producedOrderEvent = (ProducedOrderEvent) event;

        roomWrapper.removeOrder(producedOrderEvent.orderWrapper());
        balanceLabel.setText(roomWrapper.getBalancePrint());
        tokensLabel.setText(roomWrapper.getTokensPrint());
    }

    public void onStartGameEvent(IEvent event) {
        roomWrapper = GetIt.getInstance().find(RoomWrapper.class);

        ordersList.setItems(roomWrapper.getOrderModelObservableList());
        balanceLabel.setText(roomWrapper.getBalancePrint());
        tokensLabel.setText(roomWrapper.getTokensPrint());
        maxWidth = roomImageBg.getScene().getWidth();
        roomImageBg.setFitWidth(maxWidth);
    }

    public void onOrderGenerateEvent(IEvent event) {
        OrderGenerateEvent orderGenerateEvent = (OrderGenerateEvent) event;

        roomWrapper.addOrder(new OrderWrapper(orderGenerateEvent.orderWrapper()));
    }

    public void onRestockEvent(IEvent event) {
        ReStockEvent reStockEvent = (ReStockEvent) event;

        roomWrapper.restockBySupplier(reStockEvent.supplierModel());
        balanceLabel.setText(roomWrapper.getBalancePrint());
    }

    public void onSetSupplierEvent(IEvent event) {
        SetSupplierEvent setSupplierEvent = (SetSupplierEvent) event;

        roomWrapper.setNextSupplierWrapper(new SupplierWrapper(setSupplierEvent.supplierModel()));
        tokensLabel.setText(roomWrapper.getTokensPrint());
    }

    public void openStockViewEvent() {
        AnchorPane stockView = stockController.stockView;
        double prefWidthStockView = stockController.prefWidthStockView;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.3), stockView);
        translateTransition.setFromX(prefWidthStockView);
        translateTransition.setToX(0);

        Timeline timelineOpenStock = new Timeline(
                new KeyFrame(Duration.seconds(0.3), new KeyValue(stockView.prefWidthProperty(), prefWidthStockView))
        );

        Timeline timelineStrectchBg = new Timeline(
                new KeyFrame(Duration.seconds(0.3), new KeyValue(roomImageBg.fitWidthProperty(), maxWidth - prefWidthStockView))
        );

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, timelineOpenStock, timelineStrectchBg);
        parallelTransition.play();
    }

    public void closeStockViewEvent() {
        AnchorPane stockView = stockController.stockView;
        double prefWidthStockView = stockController.prefWidthStockView;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.3), stockView);
        translateTransition.setFromX(0);
        translateTransition.setToX(prefWidthStockView);

        Timeline timelineCloseStock = new Timeline(
                new KeyFrame(Duration.seconds(0.3), new KeyValue(stockView.prefWidthProperty(), 0))
        );

        Timeline timelineStrectchBg = new Timeline(
                new KeyFrame(Duration.seconds(0.3), new KeyValue(roomImageBg.fitWidthProperty(), maxWidth))
        );

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, timelineCloseStock, timelineStrectchBg);
        parallelTransition.play();
    }
}
