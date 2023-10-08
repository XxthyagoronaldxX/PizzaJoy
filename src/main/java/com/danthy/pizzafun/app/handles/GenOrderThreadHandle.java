package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.OrderGenerateEvent;
import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.domain.data.PizzaDataSingleton;
import com.danthy.pizzafun.domain.enums.NpcLevel;
import com.danthy.pizzafun.domain.models.NpcModel;
import com.danthy.pizzafun.domain.models.OrderModel;
import com.danthy.pizzafun.domain.models.PizzaModel;
import javafx.application.Platform;

import java.util.concurrent.TimeUnit;

public class GenOrderThreadHandle extends Thread implements IListener {
    private IPizzariaService roomService;

    public GenOrderThreadHandle() {}

    @Override
    public void run() {
        int maxpizzas = ApplicationProperties.roomInitialMaxPizzas;
        int mintime = ApplicationProperties.pizzaGenerationMinBaseTime;
        int maxtime = ApplicationProperties.pizzaGenerationMaxBaseTime;

        while (true) {
            try {
                int time = ((int) Math.floor((maxtime - mintime) * Math.random())) + mintime;

                TimeUnit.SECONDS.sleep(time);

                if (maxpizzas > roomService.getOrdersAmount()) {
                    NpcModel npcModel = new NpcModel("Thyago", NpcLevel.EASY);

                    PizzaModel pizzaModel = PizzaDataSingleton
                            .getInstance()
                            .getRandomPizza();

                    OrderModel orderModel = new OrderModel(npcModel, pizzaModel);

                    Platform.runLater(() -> {
                        GetIt.getInstance().find(EventPublisher.class).notifyAll(new OrderGenerateEvent(orderModel));
                    });
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            this.roomService = GetIt.getInstance().find(PizzariaServiceImpl.class);
        }
    }
}
