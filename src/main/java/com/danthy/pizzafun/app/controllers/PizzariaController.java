package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderCellListFactory;
import com.danthy.pizzafun.app.events.*;
import com.danthy.pizzafun.app.handles.OnStockViewEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.utils.TimelineUtil;
import javafx.animation.TranslateTransition;
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

public class PizzariaController extends IController implements IListener {
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

    @FXML
    public HBox upgrade;

    @FXML
    public UpgradeController upgradeController;

    @FXML
    public AnchorPane upgradeWrapperPane;

    @FXML
    public VBox upgradeViewButton;

    @FXML
    public AnchorPane notificationWrapperPane;

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

        orderListView.setCellFactory(object -> {
            OrderCellListFactory orderCellListFactory = new OrderCellListFactory();

            orderCellListFactory.prefHeightProperty().bind(headerRoomView.heightProperty().subtract(20));

            return orderCellListFactory;
        });
        orderListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        initializeStock();
        initializeTokenShop();
        initializeUpgrade();
    }

    public void initializeUpgrade() {
        upgradeController.rootView.prefWidthProperty().bind(rootView.widthProperty());
        upgradeController.rootView.prefHeightProperty().bind(rootView.heightProperty());

        upgradeViewButton.setOnMouseClicked(this::onUpgradeViewEvent);
        upgradeController.closeButton.setOnMouseClicked(this::onUpgradeViewEvent);
    }

    public void initializeTokenShop() {
        tokenShopController.rootView.prefWidthProperty().bind(rootView.widthProperty());
        tokenShopController.rootView.prefHeightProperty().bind(rootView.heightProperty());
        tokenShopViewButton.setOnMouseClicked(this::onTokenShopViewEvent);
        tokenShopWrapperPane.setOnMouseClicked(this::onTokenShopViewEvent);
    }

    public void initializeStock() {
        stockController.rootView.setPrefWidth(300);
        stockViewWidth = stockController.rootView.getPrefWidth();

        stock.prefHeightProperty().bind(stockWrapperPane.heightProperty());
        stockController.stockView.prefHeightProperty().bind(stockWrapperPane.heightProperty());
        AnchorPane.setRightAnchor(stockWrapperPane, (-1) * stockViewWidth);

        stockViewButton.setOnMouseClicked(new OnStockViewEvent(this));
    }

    public void onNotificationViewEvent(MouseEvent event) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), notificationWrapperPane);
        translateTransition.setFromX(-300);
        translateTransition.setToX(0);
        translateTransition.setCycleCount(1);
        translateTransition.play();

        notificationWrapperPane.setVisible(!notificationWrapperPane.isVisible());

        TimelineUtil.runFunctionAfterTimeInSeconds(3, (timelineEvent) -> {
            notificationWrapperPane.setVisible(!notificationWrapperPane.isVisible());
        });
    }

    public void onUpgradeViewEvent(MouseEvent event) {
        onNotificationViewEvent(event);
        upgradeWrapperPane.setVisible(!upgradeWrapperPane.isVisible());
    }

    public void onTokenShopViewEvent(MouseEvent event) {
        tokenShopWrapperPane.setVisible(!tokenShopWrapperPane.isVisible());
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) onStartGameEvent(event);
    }

    public void onStartGameEvent(IEvent event) {
        IPizzariaService pizzariaService = GetIt.getInstance().find(PizzariaServiceImpl.class);

        orderListView.setItems(pizzariaService.getOrderModelObservableList());

        Property<Double> balanceProperty = pizzariaService.getBalanceObservable().getProperty();
        Property<Integer> tokensProperty = pizzariaService.getTokensObservable().getProperty();

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
