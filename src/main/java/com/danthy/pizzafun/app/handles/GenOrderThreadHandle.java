package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.controllers.RoomController;
import com.danthy.pizzafun.app.utils.ApplicationProperties;
import com.danthy.pizzafun.app.wrappers.OrderItemListWrapper;
import com.danthy.pizzafun.domain.data.PizzaDataSingleton;
import com.danthy.pizzafun.domain.enums.NpcLevel;
import com.danthy.pizzafun.domain.models.NpcModel;
import com.danthy.pizzafun.domain.models.OrderModel;
import com.danthy.pizzafun.domain.models.PizzaModel;
import javafx.application.Platform;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GenOrderThreadHandle extends Thread {
    public RoomController roomController;

    public GenOrderThreadHandle(RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int maxpizzas = ApplicationProperties.roomInitialMaxPizzas;
                int mintime = ApplicationProperties.pizzaGenerationMinBaseTime;
                int maxtime = ApplicationProperties.pizzaGenerationMaxBaseTime;
                int time = ((int) Math.floor((maxtime-mintime) * Math.random())) + mintime;

                TimeUnit.SECONDS.sleep(time);

                if(maxpizzas > roomController.getAmountOrders()) {
                    NpcModel npcModel = new NpcModel("Thyago", NpcLevel.EASY);

                    PizzaModel pizzaModel = PizzaDataSingleton
                            .getInstance()
                            .getRandomPizza();

                    OrderModel orderModel = new OrderModel(npcModel, pizzaModel);
                    Platform.runLater(() -> {
                        roomController.addOrderEvent(orderModel);
                    });
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
