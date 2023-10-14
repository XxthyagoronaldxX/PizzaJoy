package com.danthy.pizzafun;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.*;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.threads.AutoSaveThread;
import com.danthy.pizzafun.app.threads.GenItemStockThread;
import com.danthy.pizzafun.app.threads.GenOrderThread;
import com.danthy.pizzafun.app.threads.GenSupplierThread;
import com.danthy.pizzafun.domain.data.RoomSavesXmlData;
import com.danthy.pizzafun.domain.models.RoomModel;

public class GameManager implements IListener {

    @Override
    public void update(IEvent event) {
        if (event.getClass() == GenItemStockHandleEndedEvent.class) {
            new GenItemStockThread().handle();
        } else if (event.getClass() == GenOrderHandleEndedEvent.class) {
            new GenOrderThread().handle();
        } else if (event.getClass() == GenSupplierHandleEndedEvent.class) {
            new GenSupplierThread().handle();
        } else if (event.getClass() == StartGameEvent.class) {
            new GenOrderThread().handle();
            new GenItemStockThread().handle();
            new GenSupplierThread().handle();
            new AutoSaveThread().handle();
        } else if (event.getClass() == SaveSnapshotRoomEvent.class) {
            RoomSavesXmlData roomSavesXmlData = GetIt.getInstance().find(RoomSavesXmlData.class);
            RoomModel roomModelClone = GetIt.getInstance().find(RoomModel.class).clone();

            roomSavesXmlData.save(roomModelClone);
            roomSavesXmlData.setToXml();
        }
    }
}
