package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderCellListFactory;
import com.danthy.pizzafun.app.events.*;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.states.PizzariaState;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class PizzariaController implements IListener, IController {
    @FXML
    public Label balanceLabel;

    @FXML
    public AnchorPane roomView;

    @FXML
    public Label tokensLabel;

    @FXML
    public HBox rootView;

    // @FXML public HBox tokenShop;

    // @FXML public TokenShopController tokenShopController;

    // @FXML public StockController stockController;

    @FXML
    public HBox headerRoomView;

    @FXML
    public ImageView headerRoomImageBg;

    @FXML
    public ListView orderListView;

    @FXML
    public StackPane footerRoomView;

    @FXML
    public ImageView footerRoomImageBg;

    private PizzariaServiceImpl pizzariaService;

    // private double maxWidth;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootView.widthProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();

            headerRoomImageBg.setFitWidth(value);
            footerRoomImageBg.setFitWidth(value);
        });
        rootView.heightProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();

            headerRoomImageBg.setFitHeight(value * 0.6);
            footerRoomImageBg.setFitHeight(value * 0.4);
        });

        orderListView.setItems(null);
        orderListView.setCellFactory(object -> {
            OrderCellListFactory orderCellListFactory = new OrderCellListFactory();

            orderCellListFactory.prefHeightProperty().bind(headerRoomView.heightProperty().subtract(20));

            return orderCellListFactory;
        });
        orderListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            pizzariaService = GetIt.getInstance().find(PizzariaServiceImpl.class);
            PizzariaState pizzariaState = pizzariaService.getPizzariaState();

            orderListView.setItems(pizzariaState.getOrderModelObservableList());

            initObservers();
        }
    }

    private void initObservers() {
        PizzariaState pizzariaState = pizzariaService.getPizzariaState();
        Property<Double> balanceProperty = pizzariaState.getBalanceObservable().getProperty();
        Property<Integer> tokensProperty = pizzariaState.getTokensObservable().getProperty();

        balanceProperty.addListener((observable, oldValue, newValue) -> {
            String balance = "Dinheiro: $" + newValue;

            balanceLabel.setText(balance);
        });
        tokensProperty.addListener((observable, oldValue, newValue) -> {
            String tokens = String.format("Tokens: %d TK", newValue);

            tokensLabel.setText(tokens);
        });

        balanceLabel.setText("Dinheiro: $" + balanceProperty.getValue());
        tokensLabel.setText(String.format("Tokens: %d TK", tokensProperty.getValue()));
    }

    /*
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

    public void openStockViewEvent() {
        StackPane stockView = stockController.stockView;
        double prefWidthStockView = stockController.prefWidthStockView;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.3), stockView);
        translateTransition.setFromX(prefWidthStockView);
        translateTransition.setToX(0);

        Timeline timelineOpenStock = new Timeline(
                new KeyFrame(Duration.seconds(0.3), new KeyValue(stockView.prefWidthProperty(), prefWidthStockView))
                // new KeyFrame(Duration.seconds(0.3), new KeyValue(paneAuxStrectch.prefWidthProperty(), prefWidthStockView))
        );

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, timelineOpenStock);
        parallelTransition.play();
    }

    public void closeStockViewEvent() {
        StackPane stockView = stockController.stockView;
        double prefWidthStockView = stockController.prefWidthStockView;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.3), stockView);
        translateTransition.setFromX(0);
        translateTransition.setToX(prefWidthStockView);

        Timeline timelineCloseStock = new Timeline(
                new KeyFrame(Duration.seconds(0.3), new KeyValue(stockView.prefWidthProperty(), 0))
                // new KeyFrame(Duration.seconds(0.3), new KeyValue(roomView.prefWidthProperty(), maxWidth))
        );

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, timelineCloseStock);
        parallelTransition.play();
    }*/
}
