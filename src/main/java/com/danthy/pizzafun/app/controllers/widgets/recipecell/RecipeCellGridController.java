package com.danthy.pizzafun.app.controllers.widgets.recipecell;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.events.mediator.RequestLearnRecipeEvent;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;
import com.danthy.pizzafun.app.logic.GetIt;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class RecipeCellGridController implements IController, IMediatorEmitter {

    @FXML
    public StackPane cellRoot;

    @FXML
    public Label pizzaNameLabel;

    @FXML
    public Label pizzaPriceLabel;

    @FXML
    public Button learnButton;

    @FXML
    public ProgressBar learnProgressBar;

    public RecipeWrapper recipeWrapper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void initComponents() {
        learnProgressBar.setVisible(false);
    }

    public void initCell(RecipeWrapper recipeWrapper) {
        this.recipeWrapper = recipeWrapper;

        pizzaNameLabel.setText(recipeWrapper.getPizzaModel().getName());
        pizzaPriceLabel.setText(recipeWrapper.getPizzaModel().getPriceToBuyRecipe() + " TK");

        confPopupForRecipe(recipeWrapper);

        learnButton.setOnMouseClicked(this::onRequestLearnRecipeEvent);
    }

    private void onRequestLearnRecipeEvent(MouseEvent event) {
        this.sendEvent(new RequestLearnRecipeEvent(recipeWrapper, this));
    }

    private void confPopupForRecipe(RecipeWrapper recipeWrapper) {
        Stage stage = GetIt.getInstance().find(Stage.class);
        Popup popup = new Popup();
        VBox popupContent = genContentForPopupRecipe(recipeWrapper);
        popup.getContent().add(popupContent);
        FadeTransition fadeInTransition = popupFadeInTransition(popupContent);

        cellRoot.setOnMouseEntered((event) -> {
            StackPane stackPane = (StackPane) event.getSource();

            double posX = stackPane.localToScreen(0, 0).getX() + stackPane.getWidth();
            double posY = stackPane.localToScreen(0, 0).getY();

            popup.show(stage, posX, posY);
            fadeInTransition.play();
        });

        cellRoot.setOnMouseExited((event) -> {
            popup.hide();
        });
    }

    private VBox genContentForPopupRecipe(RecipeWrapper recipeWrapper) {
        VBox vBox = new VBox();
        String items = recipeWrapper
                .getPizzaModel()
                .getItemPizzaModels()
                .stream()
                .map((item) -> item.getItemModel().getName() + " [" + item.getQuantity() + "x]")
                .reduce("", (acc, value) -> acc + value + "\n");

        Label titleLabel = new Label("Ingredientes");
        vBox.getChildren().add(titleLabel);
        titleLabel.setStyle("-fx-text-fill: white;-fx-font-family: Unispace;-fx-font-size: 18px;");

        Label itemsLabel = new Label(items);
        vBox.getChildren().add(itemsLabel);
        VBox.setVgrow(itemsLabel, Priority.ALWAYS);
        itemsLabel.setStyle("-fx-text-fill: white;-fx-font-family: Unispace;-fx-font-size: 14px;");

        vBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-background-radius: 20px; -fx-padding: 12px;");

        return vBox;
    }

    public FadeTransition popupFadeInTransition(Node content) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), content);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        return fadeTransition;
    }

    public void startToLearnTheRecipe() {
        learnButton.setVisible(false);
        learnProgressBar.setVisible(true);

        int timeInSecondsToLearn = recipeWrapper.getPizzaModel().getTimeInSecondsToLearn();
        Duration duration = Duration.seconds(timeInSecondsToLearn);
        KeyValue keyValue = new KeyValue(learnProgressBar.progressProperty(), 1.0);
        KeyFrame keyFrame = new KeyFrame(duration, keyValue);

        Timeline learnTimeline = new Timeline(keyFrame);
        learnTimeline.setCycleCount(1);
        learnTimeline.play();
    }
}
