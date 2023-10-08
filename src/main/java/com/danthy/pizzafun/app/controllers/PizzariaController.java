package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEmitter;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderCellListFactory;
import com.danthy.pizzafun.app.events.*;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.domain.models.RoomModel;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class PizzariaController extends IEmitter implements IListener, IController {
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

    private PizzariaServiceImpl pizzariaService;

    private double maxWidth;

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

        pizzariaService.removeOrder(producedOrderEvent.orderWrapper());

        refreshBalanceAndTokenInfo();
    }

    public void onStartGameEvent(IEvent event) {
        pizzariaService = GetIt.getInstance().find(PizzariaServiceImpl.class);
        PizzariaState pizzariaState = pizzariaService.getPizzariaState();

        refreshBalanceAndTokenInfo();
        ordersList.setItems(pizzariaState.getOrderModelObservableList());

        maxWidth = roomImageBg.getScene().getWidth();
        roomImageBg.setFitWidth(maxWidth);
    }

    public void onOrderGenerateEvent(IEvent event) {
        OrderGenerateEvent orderGenerateEvent = (OrderGenerateEvent) event;

        pizzariaService.addOrder(new OrderWrapper(orderGenerateEvent.orderWrapper()));
    }

    public void onRestockEvent(IEvent event) {
        ReStockEvent reStockEvent = (ReStockEvent) event;

        pizzariaService.restockBySupplier(reStockEvent.supplierModel());

        refreshBalanceAndTokenInfo();
    }

    public void onSetSupplierEvent(IEvent event) {
        refreshBalanceAndTokenInfo();
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

    public void refreshBalanceAndTokenInfo() {
        RoomModel roomModel = pizzariaService.getPizzariaState().getRoomWrapper().getWrapped();

        String balance = "Dinheiro: $" + roomModel.getBalance();
        String tokens = String.format("Tokens: %d TK", roomModel.getTokens());
        balanceLabel.setText(balance);
        tokensLabel.setText(tokens);
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

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
