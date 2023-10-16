package com.danthy.pizzafun.app.fluxs;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.contracts.IFlux;
import com.danthy.pizzafun.app.events.OrderGenerateEvent;
import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;
import com.danthy.pizzafun.app.services.PizzariaService;
import com.danthy.pizzafun.domain.enums.NpcLevel;
import com.danthy.pizzafun.domain.models.NpcModel;
import com.danthy.pizzafun.domain.models.OrderModel;
import com.danthy.pizzafun.domain.models.PizzaModel;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class GenOrderFlux extends IFlux implements IMediatorEmitter {
    private final PizzariaService pizzariaService;

    public GenOrderFlux(PizzariaService pizzariaService) {
        this.pizzariaService = pizzariaService;
    }

    @Override
    public void handle() {
        int maxpizzas = ApplicationProperties.roomInitialMaxPizzas;
        int mintime = ApplicationProperties.pizzaGenerationMinBaseTime;
        int maxtime = ApplicationProperties.pizzaGenerationMaxBaseTime;

        int time = ((int) Math.floor((maxtime - mintime) * Math.random())) + mintime;

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time)));
        timeline.setOnFinished((event) -> {
            if (maxpizzas > pizzariaService.getOrdersAmount()) {
                NpcModel npcModel = new NpcModel("Thyago", NpcLevel.EASY);

                int randomPizza = (int) (pizzariaService.getOwnedPizzaModelObservableList().size() * Math.random());

                PizzaModel pizzaModel = pizzariaService.getOwnedPizzaModelObservableList().get(randomPizza);

                OrderModel orderModel = new OrderModel(npcModel, pizzaModel);

                System.out.println(orderModel);
                this.sendEvent(new OrderGenerateEvent(orderModel));
            }

            timeline.playFromStart();
        });
    }

    public void reactOnStartGameEvent(IEvent event) {
        handle();
        play();
    }

    @Override
    public void sendEvent(IEvent event) {
        GetIt.getInstance().find(ActionsMediator.class).notify(event);
    }
}
