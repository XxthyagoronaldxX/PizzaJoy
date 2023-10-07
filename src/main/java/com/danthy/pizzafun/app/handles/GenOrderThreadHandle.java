package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.events.OrderGenerateEvent;
import com.danthy.pizzafun.app.utils.ApplicationProperties;
import com.danthy.pizzafun.app.utils.EventPublisher;
import com.danthy.pizzafun.app.utils.GetIt;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import com.danthy.pizzafun.domain.data.PizzaDataSingleton;
import com.danthy.pizzafun.domain.enums.NpcLevel;
import com.danthy.pizzafun.domain.models.NpcModel;
import com.danthy.pizzafun.domain.models.OrderModel;
import com.danthy.pizzafun.domain.models.PizzaModel;
import javafx.application.Platform;

import java.util.concurrent.TimeUnit;

public class GenOrderThreadHandle extends Thread {

    public GenOrderThreadHandle() {}

    @Override
    public void run() {
        RoomWrapper roomWrapper = GetIt.getInstance().find(RoomWrapper.class);
        int maxpizzas = ApplicationProperties.roomInitialMaxPizzas;
        int mintime = ApplicationProperties.pizzaGenerationMinBaseTime;
        int maxtime = ApplicationProperties.pizzaGenerationMaxBaseTime;

        while (true) {
            try {
                int time = ((int) Math.floor((maxtime - mintime) * Math.random())) + mintime;

                TimeUnit.SECONDS.sleep(time);

                if (maxpizzas > roomWrapper.getOrdersAmount()) {
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
}
