package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderCellListFactory;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.utils.TimelineUtil;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class PizzariaController implements IController {
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

    @FXML
    public AnchorPane notification;

    @FXML
    public NotificationController notificationController;

    @FXML
    public HBox footerRoomView;

    @FXML
    public FlowPane footerButtonsPane;

    public double stockViewWidth;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //DropShadow shadow = new DropShadow();
        //upgradeViewButton.setEffect(shadow);

        // Crie uma transição de escala para a sombra
        //shadow.radiusProperty().set(10);
        //shadow.setColor(Color.YELLOW);
        //Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(shadow.radiusProperty(), 50)));
        // Inverta a transição para criar uma animação de ida e volta
        //timeline.setAutoReverse(true);
        //timeline.setCycleCount(ScaleTransition.INDEFINITE);
        //timeline.play();


        upgradeViewButton.setOnMouseEntered((event) -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), upgradeViewButton);
            scaleTransition.setFromX(upgradeViewButton.getScaleX()); // Escala inicial no eixo X
            scaleTransition.setFromY(upgradeViewButton.getScaleY()); // Escala inicial no eixo Y
            scaleTransition.setToX(1.2);   // Escala final no eixo X (zoom de 2x)
            scaleTransition.setToY(1.2);   // Escala final no eixo Y (zoom de 2x)
            scaleTransition.setAutoReverse(true);
            scaleTransition.setCycleCount(1);
            scaleTransition.play();
        });

        upgradeViewButton.setOnMouseExited((event) -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), upgradeViewButton);
            scaleTransition.setFromX(upgradeViewButton.getScaleX()); // Escala inicial no eixo X
            scaleTransition.setFromY(upgradeViewButton.getScaleY()); // Escala inicial no eixo Y
            scaleTransition.setToX(1);   // Escala final no eixo X (zoom de 2x)
            scaleTransition.setToY(1);   // Escala final no eixo Y (zoom de 2x)
            scaleTransition.setAutoReverse(true);
            scaleTransition.setCycleCount(1);
            scaleTransition.play();
        });



        roomView.widthProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();

            headerRoomImageBg.setFitWidth(value);
            footerRoomImageBg.setFitWidth(value);
        });
        roomView.heightProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();

            headerRoomImageBg.setFitHeight(value * 0.6);
            headerRoomView.setPrefHeight(value * 0.6);
            footerRoomView.setPrefHeight(value * 0.4);
            footerButtonsPane.setPrefHeight(value * 0.2);
            footerRoomImageBg.setFitHeight(value * 0.2);
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

    private void initializeUpgrade() {
        upgradeController.rootView.prefWidthProperty().bind(rootView.widthProperty());
        upgradeController.rootView.prefHeightProperty().bind(rootView.heightProperty());

        upgradeViewButton.setOnMouseClicked(this::onUpgradeViewEvent);
        upgradeController.closeButton.setOnMouseClicked(this::onUpgradeViewEvent);
    }

    private void initializeTokenShop() {
        tokenShopController.rootView.prefWidthProperty().bind(rootView.widthProperty());
        tokenShopController.rootView.prefHeightProperty().bind(rootView.heightProperty());
        tokenShopViewButton.setOnMouseClicked(this::onTokenShopViewEvent);
        tokenShopWrapperPane.setOnMouseClicked(this::onTokenShopViewEvent);
    }

    private void initializeStock() {
        stockController.rootView.setPrefWidth(300);
        stockViewWidth = stockController.rootView.getPrefWidth();

        stock.prefHeightProperty().bind(stockWrapperPane.heightProperty());
        stockController.stockView.prefHeightProperty().bind(stockWrapperPane.heightProperty());
        AnchorPane.setRightAnchor(stockWrapperPane, (-1) * stockViewWidth);

        stockViewButton.setOnMouseClicked(this::onStockViewEvent);
    }

    private void onUpgradeViewEvent(MouseEvent event) {
        upgradeWrapperPane.setVisible(!upgradeWrapperPane.isVisible());
    }

    private void onTokenShopViewEvent(MouseEvent event) {
        tokenShopWrapperPane.setVisible(!tokenShopWrapperPane.isVisible());
    }

    private void onStockViewEvent(MouseEvent event) {
        Platform.runLater(() -> {
            double fromRightAnchor = AnchorPane.getRightAnchor(roomView) == 0 ? 0 : stockViewWidth;
            double toRightAnchor = AnchorPane.getRightAnchor(roomView) == 0 ? stockViewWidth : 0;

            ObservableValue<Double> rightAnchorObservable = new ObservableValue<>(fromRightAnchor);

            rightAnchorObservable.getProperty().addListener((observable, oldValue, newValue) -> {
                AnchorPane.setRightAnchor(roomView, newValue);
                AnchorPane.setRightAnchor(stockWrapperPane, (-1) * stockViewWidth + newValue);
            });

            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(rightAnchorObservable.getProperty(), toRightAnchor);

            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.4), keyValue);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
        });
    }

    public void reactOnStartGameEvent(IEvent event) {
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

    public void reactOnNotifyEvent(IEvent event) {
        if (notificationWrapperPane.isVisible()) return;


        TranslateTransition translateTransitionStart = new TranslateTransition(Duration.seconds(0.3), notificationWrapperPane);
        translateTransitionStart.setFromX(-300);
        translateTransitionStart.setToX(0);
        translateTransitionStart.setCycleCount(1);
        translateTransitionStart.play();

        notificationWrapperPane.setVisible(true);

        TimelineUtil.runFunctionAfterTimeInSeconds(3, (timelineEventStart) -> {
            TranslateTransition translateTransitionEnd = new TranslateTransition(Duration.seconds(0.6), notificationWrapperPane);
            translateTransitionEnd.setFromX(0);
            translateTransitionEnd.setToX(-300);
            translateTransitionEnd.setCycleCount(1);
            translateTransitionEnd.play();

            translateTransitionEnd.setOnFinished((timelineEventEnd) -> {
                notificationWrapperPane.setVisible(false);
            });
        });
    }
}
