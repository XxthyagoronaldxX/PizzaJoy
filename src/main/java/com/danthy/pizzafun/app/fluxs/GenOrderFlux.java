package com.danthy.pizzafun.app.fluxs;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.contracts.Flux;
import com.danthy.pizzafun.app.events.mediator.OrderGenerateEvent;
import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.domain.enums.NpcLevel;
import com.danthy.pizzafun.domain.models.NpcModel;
import com.danthy.pizzafun.domain.models.OrderModel;
import com.danthy.pizzafun.domain.models.PizzaModel;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class GenOrderFlux extends Flux implements IMediatorEmitter {
    private final IPizzariaService pizzariaService;

    public GenOrderFlux(IPizzariaService pizzariaService) {
        this.pizzariaService = pizzariaService;
    }

    @Override
    public void initFlux() {
        int mintime = ApplicationProperties.pizzaGenerationMinBaseTime;
        int maxtime = ApplicationProperties.pizzaGenerationMaxBaseTime;

        int time = ((int) Math.floor((maxtime - mintime) * Math.random())) + mintime;

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time)));
    }

    @Override
    public void onFinished(ActionEvent event) {
        int maxpizzas = ApplicationProperties.roomInitialMaxPizzas;

        if (maxpizzas > pizzariaService.getOrdersAmount()) {
            NpcModel npcModel = new NpcModel("Thyago", NpcLevel.EASY);

            int randomPizza = (int) (pizzariaService.getOwnedPizzaModelObservableList().size() * Math.random());

            PizzaModel pizzaModel = pizzariaService.getOwnedPizzaModelObservableList().get(randomPizza);

            OrderModel orderModel = new OrderModel(npcModel, pizzaModel);

            this.sendEvent(new OrderGenerateEvent(orderModel));
        }
    }

    public void reactOnOrderGenerateEvent(IEvent event) {play();}

    public void reactOnStartGameEvent(IEvent event) {
        play();
    }
}
