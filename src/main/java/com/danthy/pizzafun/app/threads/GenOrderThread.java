package com.danthy.pizzafun.app.threads;

import com.danthy.pizzafun.app.contracts.IHandle;
import com.danthy.pizzafun.app.events.GenOrderHandleEndedEvent;
import com.danthy.pizzafun.app.events.OrderGenerateEvent;
import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.domain.data.PizzaDataSingleton;
import com.danthy.pizzafun.domain.enums.NpcLevel;
import com.danthy.pizzafun.domain.models.NpcModel;
import com.danthy.pizzafun.domain.models.OrderModel;
import com.danthy.pizzafun.domain.models.PizzaModel;
import com.danthy.pizzafun.domain.models.RoomModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GenOrderThread implements IHandle {
    private final PizzariaServiceImpl pizzariaService;
    private final EventPublisher eventPublisher;

    public GenOrderThread() {
        pizzariaService = GetIt.getInstance().find(PizzariaServiceImpl.class);
        eventPublisher = GetIt.getInstance().find(EventPublisher.class);
    }

    @Override
    public void handle() {
        PizzariaState pizzariaState = pizzariaService.getPizzariaState();
        int maxpizzas = ApplicationProperties.roomInitialMaxPizzas;
        int mintime = ApplicationProperties.pizzaGenerationMinBaseTime;
        int maxtime = ApplicationProperties.pizzaGenerationMaxBaseTime;

        int time = ((int) Math.floor((maxtime - mintime) * Math.random())) + mintime;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(time)));

        timeline.setOnFinished((event) -> {
            if (maxpizzas > pizzariaService.getOrdersAmount()) {
                NpcModel npcModel = new NpcModel("Thyago", NpcLevel.EASY);

                int randomPizza = (int) (pizzariaState.getOwnedPizzaModelObservableList().size() * Math.random());

                PizzaModel pizzaModel = pizzariaState.getOwnedPizzaModelObservableList().get(randomPizza);

                OrderModel orderModel = new OrderModel(npcModel, pizzaModel);

                eventPublisher.notifyAll(new OrderGenerateEvent(orderModel));
            }

            eventPublisher.notifyAll(new GenOrderHandleEndedEvent());
        });

        timeline.play();
    }
}
