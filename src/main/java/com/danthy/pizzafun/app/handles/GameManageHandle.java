package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.GenItemStockHandleEndedEvent;
import com.danthy.pizzafun.app.events.GenOrderHandleEndedEvent;
import com.danthy.pizzafun.app.events.GenSupplierHandleEndedEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;

public class GameManageHandle implements IListener {

    @Override
    public void update(IEvent event) {
        if (event.getClass() == GenItemStockHandleEndedEvent.class) {
            new GenItemStockHandle().handle();
        } else if (event.getClass() == GenOrderHandleEndedEvent.class) {
            new GenOrderHandle().handle();
        } else if (event.getClass() == GenSupplierHandleEndedEvent.class) {
            new GenSupplierHandle().handle();
        } else if (event.getClass() == StartGameEvent.class) {
            new GenOrderHandle().handle();
            new GenItemStockHandle().handle();
            new GenSupplierHandle().handle();
        }
    }
}
