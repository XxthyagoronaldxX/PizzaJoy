package com.danthy.pizzafun.app.controllers.pizzaria.subviews;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.ReactOn;
import com.danthy.pizzafun.app.controllers.pizzaria.widgets.recipecell.RecipeCellGridWrapper;
import com.danthy.pizzafun.app.controllers.pizzaria.widgets.recipecell.RecipeWrapper;
import com.danthy.pizzafun.app.controllers.pizzaria.widgets.suppliercell.SupplierCellListFactory;
import com.danthy.pizzafun.app.events.mediator.StartGameEvent;
import com.danthy.pizzafun.app.events.mediator.SuccessLearnRecipeEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.ITokenShopService;
import com.danthy.pizzafun.app.services.implementations.TokenShopServiceImpl;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.*;

import java.util.List;

public class TokenShopController implements IController {
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


    @Override
    public void initComponents() {
        supplierList.setCellFactory(object -> new SupplierCellListFactory());
        supplierList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @ReactOn(StartGameEvent.class)
    public void reactOnStartGameEvent(IEvent event) {
        tokenShopService = GetIt.getInstance().find(TokenShopServiceImpl.class);

        supplierList.setItems(tokenShopService.getSupplierModelObservableList());

        tokenShopService.getCurrentSupplierProperty().addListener((observer, oldValue, newValue) -> {
            refreshSupplier(newValue);
        });

        refreshSupplier(tokenShopService.getCurrentSupplierProperty().getValue());

        tokenShopService.getRecipeWrapperObservableList().addListener((ListChangeListener<? super RecipeWrapper>) change -> {
            buildRecipeGridView();
        });

        buildRecipeGridView();
    }

    private void buildRecipeGridView() {
        List<RecipeWrapper> recipeWrapperList = tokenShopService.getRecipeWrapperObservableList();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        int nColumn = 2;
        for (int i = 0; i < recipeWrapperList.size(); i++) {
            VBox recipeCellGridWrapper = new RecipeCellGridWrapper().build(recipeWrapperList.get(i));

            gridPane.add(recipeCellGridWrapper, i % nColumn, i / nColumn);
        }

        recipeListScroll.setContent(gridPane);
        gridPane.prefWidthProperty().bind(recipeListScroll.widthProperty());
    }

    private void refreshSupplier(SupplierModel supplierModel) {
        String name = supplierModel.getName();
        String bonusChance = "Chance de Bonus: " + supplierModel.getBonusChance() + "%";
        String deliveryTimeInSeconds = "Tempo: " + supplierModel.getDeliveryTimeInSeconds() + "s";

        supplierNameLabel.setText(name);
        supplierBonusChanceLabel.setText(bonusChance);
        supplierRestockTimeLabel.setText(deliveryTimeInSeconds);
    }
}
