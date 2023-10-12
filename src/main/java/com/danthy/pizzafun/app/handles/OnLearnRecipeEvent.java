package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.controllers.widgets.recipecell.RecipeCellGridController;
import com.danthy.pizzafun.app.controllers.widgets.recipecell.RecipeWrapper;
import com.danthy.pizzafun.app.events.LearnRecipeEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class OnLearnRecipeEvent implements EventHandler<MouseEvent> {
    private final ProgressBar learnProgressBar;
    private final Button learnButton;
    private final RecipeWrapper recipeWrapper;
    private final IPizzariaService pizzariaService;
    private final EventPublisher eventPublisher;

    public OnLearnRecipeEvent(RecipeCellGridController controller) {
        recipeWrapper = controller.recipeWrapper;
        learnProgressBar = controller.learnProgressBar;
        learnButton = controller.learnButton;

        pizzariaService = GetIt.getInstance().find(PizzariaServiceImpl.class);
        eventPublisher = GetIt.getInstance().find(EventPublisher.class);
    }

    @Override
    public void handle(MouseEvent event) {
        if (pizzariaService.getTokens() < recipeWrapper.getPizzaModel().getPriceToBuyRecipe()) return;

        learnButton.setVisible(false);
        learnProgressBar.setVisible(true);

        eventPublisher.notifyAll(new LearnRecipeEvent(recipeWrapper));

        int timeInSecondsToLearn = recipeWrapper.getPizzaModel().getTimeInSecondsToLearn();
        Duration duration = Duration.seconds(timeInSecondsToLearn);
        KeyValue keyValue = new KeyValue(learnProgressBar.progressProperty(), 1.0);
        KeyFrame keyFrame = new KeyFrame(duration, keyValue);

        Timeline learnTimeline = new Timeline(keyFrame);
        learnTimeline.setCycleCount(1);
        learnTimeline.play();
    }
}
