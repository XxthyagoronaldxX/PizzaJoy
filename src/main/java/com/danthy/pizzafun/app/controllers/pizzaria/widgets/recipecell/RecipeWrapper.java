package com.danthy.pizzafun.app.controllers.pizzaria.widgets.recipecell;

import com.danthy.pizzafun.domain.models.PizzaModel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RecipeWrapper {
    private PizzaModel pizzaModel;

    private Double currentProgress;

    public RecipeWrapper(PizzaModel pizzaModel) {
        this.pizzaModel = pizzaModel;
        this.currentProgress = 0.0;
    }
}
