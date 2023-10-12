package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEmitter;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.recipecell.RecipeCellGridWrapper;
import com.danthy.pizzafun.app.controllers.widgets.recipecell.RecipeWrapper;
import com.danthy.pizzafun.app.controllers.widgets.suppliercell.SupplierCellListFactory;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.services.ITokenShopService;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.services.implementations.TokenShopServiceImpl;
import com.danthy.pizzafun.app.states.TokenShopState;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TokenShopController extends IEmitter implements IController, IListener {
    @FXML
    public ListView supplierList;

    @FXML
    public Label supplierNameLabel;

    @FXML
    public Label supplierRestockTimeLabel;

    @FXML
    public Label supplierBonusChanceLabel;

    @FXML
    public Label supplierCostLabel;

    @FXML
    public AnchorPane rootView;

    @FXML
    public VBox recipeVBox;

    @FXML
    public ScrollPane recipeListScroll;

    private ITokenShopService tokenShopService;

    private IPizzariaService pizzariaService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        supplierList.setCellFactory(object -> new SupplierCellListFactory());
        supplierList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void initObservers() {
        Property<SupplierModel> currentSupplierProperty = tokenShopService.getCurrentSupplierProperty();

        currentSupplierProperty.addListener((observer, oldValue, newValue) -> {
            refreshSupplier(newValue);
        });

        refreshSupplier(currentSupplierProperty.getValue());
    }

    public void refreshSupplier(SupplierModel supplierModel) {
        String name = supplierModel.getName();
        String cost = "Custo: R$" + supplierModel.getCost();
        String bonusChance = "Chance de Bonus: " + supplierModel.getBonusChance() + "%";
        String deliveryTimeInSeconds = "Tempo: " + supplierModel.getDeliveryTimeInSeconds() + "s";

        supplierNameLabel.setText(name);
        supplierCostLabel.setText(cost);
        supplierBonusChanceLabel.setText(bonusChance);
        supplierRestockTimeLabel.setText(deliveryTimeInSeconds);
    }

    public void initRecipeGridView() {
        TokenShopState tokenShopState = tokenShopService.getTokenShopState();
        List<RecipeWrapper> recipeWrapperList = tokenShopState.getRecipeWrapperObservableList();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        int nColumn = 2;
        for (int i = 0; i < recipeWrapperList.size(); i++) {
            VBox recipeCellGridWrapper = new RecipeCellGridWrapper().build(recipeWrapperList.get(i), pizzariaService);

            gridPane.add(recipeCellGridWrapper, i % nColumn, i / nColumn);
        }

        recipeListScroll.setContent(gridPane);
        gridPane.prefWidthProperty().bind(recipeListScroll.widthProperty());
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        super.eventPublisher = eventPublisher;
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            tokenShopService = GetIt.getInstance().find(TokenShopServiceImpl.class);
            pizzariaService = GetIt.getInstance().find(PizzariaServiceImpl.class);

            TokenShopState tokenShopState = tokenShopService.getTokenShopState();

            supplierList.setItems(tokenShopState.getSupplierModelObservableList());

            initRecipeGridView();
            initObservers();
        }
    }
}
