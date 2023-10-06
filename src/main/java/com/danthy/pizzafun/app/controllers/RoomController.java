package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.Controller;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.itemstockcell.ItemStockCellListFactory;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderCellListFactory;
import com.danthy.pizzafun.app.events.ProducedOrderEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.utils.EventPublisher;
import com.danthy.pizzafun.app.utils.GetIt;
import com.danthy.pizzafun.app.wrappers.OrderItemListWrapper;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import com.danthy.pizzafun.domain.models.OrderModel;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class RoomController extends Controller implements IListener {
    @FXML
    public ListView itemStockList;

    @FXML
    public ListView ordersList;

    @FXML
    public Label balanceLabel;

    @FXML
    public AnchorPane stockView;

    @FXML
    public AnchorPane roomView;

    @FXML
    public HBox rootView;

    @FXML
    public ImageView roomImageBg;

    public double prefWidthStockView;

    private double maxWidth;

    private RoomWrapper roomWrapper;

    public RoomController() {}

    public void addOrderEvent(OrderModel orderModel) {
        roomWrapper.addOrder(new OrderItemListWrapper(orderModel));
    }

    public int getAmountOrders() {
        return roomWrapper.getOrdersAmount();
    }

    @Override
    public void initialize() {
        initOrderListView();
        initStockView();
        initItemStockListView();
    }

    private void initItemStockListView() {
        itemStockList.setCellFactory(object -> new ItemStockCellListFactory());
        itemStockList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void initStockView() {
        prefWidthStockView = stockView.getPrefWidth();
        stockView.setTranslateX(prefWidthStockView);
        stockView.setPrefWidth(0);
    }

    private void initOrderListView() {
        ordersList.setCellFactory(object -> new OrderCellListFactory());
        ordersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == ProducedOrderEvent.class) {
            ProducedOrderEvent producedOrderEvent = (ProducedOrderEvent) event;

            roomWrapper.removeOrder(producedOrderEvent.orderItemListWrapper());
            balanceLabel.setText(roomWrapper.getBalancePrint());
        } else if (event.getClass() == StartGameEvent.class) {
            roomWrapper = GetIt.getInstance().find(RoomWrapper.class);

            itemStockList.setItems(roomWrapper.getItemStockModelObservableList());
            ordersList.setItems(roomWrapper.getOrderModelObservableList());
            balanceLabel.setText(roomWrapper.getBalancePrint());
            maxWidth = roomImageBg.getScene().getWidth();
            roomImageBg.setFitWidth(maxWidth);
        }
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @FXML
    public void stockViewEvent(MouseEvent mouseEvent) {
        if (stockView.getPrefWidth() != prefWidthStockView) {
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
        } else {
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
}
