package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderCellListFactory;
import com.danthy.pizzafun.app.events.*;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import com.danthy.pizzafun.domain.models.RoomModel;
import javafx.animation.*;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class PizzariaController implements IListener, IController {
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
    public HBox tokenShop;

    @FXML
    public TokenShopController tokenShopController;

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
        if (event.getClass() == StartGameEvent.class) {
            pizzariaService = GetIt.getInstance().find(PizzariaServiceImpl.class);
            PizzariaState pizzariaState = pizzariaService.getPizzariaState();

            ordersList.setItems(pizzariaState.getOrderModelObservableList());

            maxWidth = roomImageBg.getScene().getWidth();
            roomImageBg.setFitWidth(maxWidth);

            initObservers();
        }
    }

    private void initObservers() {
        RoomWrapper roomWrapper = pizzariaService.getPizzariaState().getRoomWrapper();
        Property<Double> balanceProperty = roomWrapper.getBalanceObservableValue().getProperty();
        Property<Integer> tokensProperty = roomWrapper.getTokensObservableValue().getProperty();
        RoomModel roomModel = roomWrapper.getWrapped();

        balanceProperty.addListener((observable, oldValue, newValue) -> {
            String balance = "Dinheiro: $" + newValue;

            balanceLabel.setText(balance);
        });
        tokensProperty.addListener((observable, oldValue, newValue) -> {
            String tokens = String.format("Tokens: %d TK", newValue);

            tokensLabel.setText(tokens);
        });

        balanceLabel.setText("Dinheiro: $" + roomModel.getBalance());
        tokensLabel.setText(String.format("Tokens: %d TK", roomModel.getTokens()));
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
