package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.GenItemStockThreadEndedEvent;
import com.danthy.pizzafun.app.events.GenOrderThreadEndedEvent;
import com.danthy.pizzafun.app.events.GenSupplierThreadEndedEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;

public class GameThreadHandle implements IListener {

    @Override
    public void update(IEvent event) {
        if (event.getClass() == GenItemStockThreadEndedEvent.class) {
            new GenItemStockThreadHandle().start();
        } else if (event.getClass() == GenOrderThreadEndedEvent.class) {
            new GenOrderThreadHandle().start();
        } else if (event.getClass() == GenSupplierThreadEndedEvent.class) {
            new GenSupplierThreadHandle().start();
        } else if (event.getClass() == StartGameEvent.class) {
            new GenItemStockThreadHandle().start();
            new GenOrderThreadHandle().start();
            new GenSupplierThreadHandle().start();
        }
    }
}
