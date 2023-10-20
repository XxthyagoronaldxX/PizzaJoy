package com.danthy.pizzafun.app.managers;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IManager;
import com.danthy.pizzafun.app.contracts.ReactOn;
import com.danthy.pizzafun.app.events.mediator.SaveSnapshotRoomEvent;
import com.danthy.pizzafun.app.events.mediator.StartGameEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.app.states.StockState;
import com.danthy.pizzafun.app.states.TokenShopState;
import com.danthy.pizzafun.app.states.UpgradeState;
import com.danthy.pizzafun.app.utils.JaxbUtil;
import com.danthy.pizzafun.domain.data.PizzaXmlData;
import com.danthy.pizzafun.domain.data.RoomSavesXmlData;
import com.danthy.pizzafun.domain.models.PizzaModel;
import com.danthy.pizzafun.domain.models.RoomModel;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements IManager {
    @ReactOn(SaveSnapshotRoomEvent.class)
    public void reactOnSaveSnapshotRoomEvent(IEvent event) {
        RoomSavesXmlData roomSavesXmlData = GetIt.getInstance().find(RoomSavesXmlData.class);
        RoomModel roomModelClone = GetIt.getInstance().find(RoomModel.class).clone();

        roomSavesXmlData.save(roomModelClone);
        roomSavesXmlData.setToXml();
    }

    @ReactOn(StartGameEvent.class)
    public void reactOnStartGameEvent(IEvent event) {
        StartGameEvent startGameEvent = (StartGameEvent) event;

        List<PizzaModel> pizzaModelList = new ArrayList<>(PizzaXmlData.getFromXml().getPizzaModelList());
        RoomModel roomModel = JaxbUtil.unmarshaller(RoomModel.class, "FirstGameRoomConfig");
        roomModel.setName(startGameEvent.roomName());

        GetIt.getInstance()
                .addSingleton(new StockState(roomModel))
                .addSingleton(new PizzariaState(roomModel))
                .addSingleton(new TokenShopState(roomModel, pizzaModelList))
                .addSingleton(new UpgradeState(roomModel))
                .addSingleton(roomModel);
    }
}
