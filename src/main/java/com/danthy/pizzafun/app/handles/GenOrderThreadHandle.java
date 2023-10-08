package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.events.GenOrderThreadEndedEvent;
import com.danthy.pizzafun.app.events.OrderGenerateEvent;
import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.domain.data.PizzaDataSingleton;
import com.danthy.pizzafun.domain.enums.NpcLevel;
import com.danthy.pizzafun.domain.models.NpcModel;
import com.danthy.pizzafun.domain.models.OrderModel;
import com.danthy.pizzafun.domain.models.PizzaModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

public class GenOrderThreadHandle extends Thread {
    private final IPizzariaService pizzariaService;
    private final EventPublisher eventPublisher;

    public GenOrderThreadHandle() {
        pizzariaService = GetIt.getInstance().find(PizzariaServiceImpl.class);
        eventPublisher = GetIt.getInstance().find(EventPublisher.class);
    }

    @Override
    public void run() {
        int maxpizzas = ApplicationProperties.roomInitialMaxPizzas;
        int mintime = ApplicationProperties.pizzaGenerationMinBaseTime;
        int maxtime = ApplicationProperties.pizzaGenerationMaxBaseTime;


        int time = ((int) Math.floor((maxtime - mintime) * Math.random())) + mintime;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(time)));

        timeline.setOnFinished((event) -> {
            if (maxpizzas > pizzariaService.getOrdersAmount()) {
                NpcModel npcModel = new NpcModel("Thyago", NpcLevel.EASY);

                PizzaModel pizzaModel = PizzaDataSingleton
                        .getInstance()
                        .getRandomPizza();

                OrderModel orderModel = new OrderModel(npcModel, pizzaModel);

                Platform.runLater(() -> {
                    eventPublisher.notifyAll(new OrderGenerateEvent(orderModel));
                });
            }
        });

        timeline.play();

        while (timeline.getStatus() == Animation.Status.RUNNING) Thread.onSpinWait();

        eventPublisher.notifyAll(new GenOrderThreadEndedEvent());
    }
}
