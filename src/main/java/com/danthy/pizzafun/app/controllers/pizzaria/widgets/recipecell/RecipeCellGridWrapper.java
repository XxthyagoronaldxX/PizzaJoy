package com.danthy.pizzafun.app.controllers.pizzaria.widgets.recipecell;

import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RecipeCellGridWrapper extends VBox {
    public VBox build(RecipeWrapper recipeWrapper) {
        FXMLLoader loader = FxmlUtil.loaderFromName(PathFxmlUtil.RECIPE_CELL_GRID_WIDGET);

        try {
            StackPane stackPane = loader.load();
            RecipeCellGridController controller = loader.getController();
            controller.initCell(recipeWrapper);

            getChildren().add(stackPane);
            setVgrow(stackPane, Priority.ALWAYS);

            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
