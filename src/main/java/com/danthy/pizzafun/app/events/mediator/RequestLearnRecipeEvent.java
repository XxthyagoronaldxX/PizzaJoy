package com.danthy.pizzafun.app.events.mediator;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.controllers.widgets.recipecell.RecipeCellGridController;
import com.danthy.pizzafun.app.controllers.widgets.recipecell.RecipeWrapper;

public record RequestLearnRecipeEvent(RecipeWrapper recipeWrapper, RecipeCellGridController controller) implements IEvent {
}
