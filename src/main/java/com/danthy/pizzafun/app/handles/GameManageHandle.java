package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.GenItemStockThreadEndedEvent;
import com.danthy.pizzafun.app.events.GenOrderThreadEndedEvent;
import com.danthy.pizzafun.app.events.GenSupplierThreadEndedEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;

public class GameManageHandle implements IListener {

    @Override
    public void update(IEvent event) {
        if (event.getClass() == GenItemStockThreadEndedEvent.class) {
            new GenItemStockHandle().handle();
        } else if (event.getClass() == GenOrderThreadEndedEvent.class) {
            new GenOrderHandle().handle();
        } else if (event.getClass() == GenSupplierThreadEndedEvent.class) {
            new GenSupplierHandle().handle();
        } else if (event.getClass() == StartGameEvent.class) {
            new GenOrderHandle().handle();
            new GenItemStockHandle().handle();
            new GenSupplierHandle().handle();
        }
    }
}
