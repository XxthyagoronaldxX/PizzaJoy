package com.danthy.pizzafun.app.threads;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.GenItemStockHandleEndedEvent;
import com.danthy.pizzafun.app.events.GenOrderHandleEndedEvent;
import com.danthy.pizzafun.app.events.GenSupplierHandleEndedEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;

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
        }
    }
}
