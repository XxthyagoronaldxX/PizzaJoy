package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderCellListFactory;
import com.danthy.pizzafun.app.events.*;
import com.danthy.pizzafun.app.handles.OnStockViewEvent;
import com.danthy.pizzafun.app.handles.OnTokenShopViewEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.states.PizzariaState;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PizzariaController implements IListener, IController {
    @FXML
    public Label balanceLabel;

    @FXML
    public AnchorPane roomView;

    @FXML
    public Label tokensLabel;

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

    @FXML
    public StockController stockController;

    @FXML
    public VBox stock;

    @FXML
    public AnchorPane stockWrapperPane;

    @FXML
    public AnchorPane tokenShop;

    @FXML
    public TokenShopController tokenShopController;

    @FXML
    public AnchorPane tokenShopWrapperPane;

    @FXML
    public AnchorPane rootView;

    @FXML
    public VBox stockViewButton;

    @FXML
    public VBox tokenShopViewButton;

    public double stockViewWidth;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomView.widthProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();

            headerRoomImageBg.setFitWidth(value);
            footerRoomImageBg.setFitWidth(value);
        });
        roomView.heightProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();

            headerRoomImageBg.setFitHeight(value * 0.6);
            footerRoomImageBg.setFitHeight(value * 0.4);
        });

        stockController.rootView.setPrefWidth(300);
        stockViewWidth = stockController.rootView.getPrefWidth();
        stock.prefHeightProperty().bind(stockWrapperPane.heightProperty());
        stockController.stockView.prefHeightProperty().bind(stockWrapperPane.heightProperty());
        AnchorPane.setRightAnchor(stockWrapperPane, (-1) * stockViewWidth);

        tokenShopController.rootView.prefWidthProperty().bind(rootView.widthProperty());
        tokenShopController.rootView.prefHeightProperty().bind(rootView.heightProperty());
        tokenShopWrapperPane.setVisible(false);

        orderListView.setCellFactory(object -> {
            OrderCellListFactory orderCellListFactory = new OrderCellListFactory();

            orderCellListFactory.prefHeightProperty().bind(headerRoomView.heightProperty().subtract(20));

            return orderCellListFactory;
        });
        orderListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        stockViewButton.setOnMouseClicked(new OnStockViewEvent(this));
        tokenShopViewButton.setOnMouseClicked(new OnTokenShopViewEvent(this));
        tokenShopWrapperPane.setOnMouseClicked(new OnTokenShopViewEvent(this));
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            PizzariaState pizzariaState = GetIt.getInstance().find(PizzariaServiceImpl.class).getPizzariaState();

            orderListView.setItems(pizzariaState.getOrderModelObservableList());

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
    }
}
