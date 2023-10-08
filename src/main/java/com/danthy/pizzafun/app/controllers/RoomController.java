package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEmitter;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.itemstockcell.ItemStockCellListFactory;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderCellListFactory;
import com.danthy.pizzafun.app.controllers.widgets.suppliercell.SupplierCellListFactory;
import com.danthy.pizzafun.app.events.*;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;
import com.danthy.pizzafun.app.wrappers.UpgradeWrapper;
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
    public ImageView roomImageBg;

    @FXML
    public AnchorPane stockPaneBg;

    @FXML
    public ImageView stockImageBg;

    @FXML
    public HBox upgradeViewRoot;

    @FXML
    public AnchorPane upgradeViewSubRoot;

    @FXML
    public HBox rootView;

    @FXML
    public ListView supplierList;

    @FXML
    public Label tokensLabel;

    @FXML
    public AnchorPane upgradeViewContainer;

    @FXML
    public Label supplierNameLabel;

    @FXML
    public Label supplierRestockTimeLabel;

    @FXML
    public Label supplierBonusChanceLabel;

    @FXML
    public Label supplierCostLabel;

    public double prefWidthStockView;

    private double maxWidth;

    private RoomWrapper roomWrapper;

    private UpgradeWrapper upgradeWrapper;

    public RoomController() {}

    @Override
    public void initialize() {
        initUpgradeView();
        initOrderListView();
        initStockView();
        initItemStockListView();
        initSupplierListView();
    }

    private void initSupplierListView() {
        supplierList.setCellFactory(object -> new SupplierCellListFactory());
        supplierList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void initUpgradeView() {
        roomView.widthProperty().addListener((observable, oldValue, newValue) -> {
            upgradeViewSubRoot.setPrefWidth(newValue.doubleValue());
        });

        upgradeViewRoot.toBack();
    }

    private void initItemStockListView() {
        itemStockList.setCellFactory(object -> new ItemStockCellListFactory());
        itemStockList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void initStockView() {
        prefWidthStockView = stockView.getPrefWidth();
        stockView.setTranslateX(prefWidthStockView);
        stockView.setPrefWidth(0);

        stockPaneBg.widthProperty().addListener((observable, oldValue, newValue) -> {
            stockImageBg.setFitWidth(newValue.doubleValue());
        });
        stockPaneBg.heightProperty().addListener((observable, oldValue, newValue) -> {
            stockImageBg.setFitHeight(newValue.doubleValue());
        });
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
        } else if (event.getClass() == SupplierGenerateEvent.class) {
            onSupplierGenerateEvent(event);
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
        upgradeWrapper = GetIt.getInstance().find(UpgradeWrapper.class);

        itemStockList.setItems(roomWrapper.getItemStockModelObservableList());
        ordersList.setItems(roomWrapper.getOrderModelObservableList());
        supplierList.setItems(upgradeWrapper.getSupplierModelObservableList());

        balanceLabel.setText(roomWrapper.getBalancePrint());
        tokensLabel.setText(roomWrapper.getTokensPrint());
        maxWidth = roomImageBg.getScene().getWidth();
        roomImageBg.setFitWidth(maxWidth);

        refreshCurrentSupplier();
    }

    public void onSupplierGenerateEvent(IEvent event) {
        SupplierGenerateEvent supplierGenerateEvent = (SupplierGenerateEvent) event;

        upgradeWrapper.addAllSupplierModel(supplierGenerateEvent.supplierModelList());
    }

    public void onOrderGenerateEvent(IEvent event) {
        OrderGenerateEvent orderGenerateEvent = (OrderGenerateEvent) event;

        roomWrapper.addOrder(new OrderWrapper(orderGenerateEvent.orderWrapper()));
    }

    public void onRestockEvent(IEvent event) {
        ReStockEvent reStockEvent = (ReStockEvent) event;

        roomWrapper.restockBySupplier(reStockEvent.supplierModel());
        balanceLabel.setText(roomWrapper.getBalancePrint());

        refreshCurrentSupplier();
    }

    public void onSetSupplierEvent(IEvent event) {
        SetSupplierEvent setSupplierEvent = (SetSupplierEvent) event;

        roomWrapper.setNextSupplierWrapper(new SupplierWrapper(setSupplierEvent.supplierModel()));
        tokensLabel.setText(roomWrapper.getTokensPrint());
    }

    public void refreshCurrentSupplier() {
        SupplierWrapper supplierWrapper = roomWrapper.getSupplierWrapper();
        supplierNameLabel.setText(supplierWrapper.getNamePrint());
        supplierCostLabel.setText(supplierWrapper.getCostPrint());
        supplierBonusChanceLabel.setText(supplierWrapper.getBonusChancePrint());
        supplierRestockTimeLabel.setText(supplierWrapper.getDeliveryTimeInSecondsPrint());
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

    @FXML
    public void closeUpgradeViewEvent(MouseEvent mouseEvent) {
        upgradeViewRoot.toBack();
    }

    @FXML
    public void openUpgradeViewEvent(MouseEvent mouseEvent) {
        upgradeViewRoot.toFront();
    }
}
