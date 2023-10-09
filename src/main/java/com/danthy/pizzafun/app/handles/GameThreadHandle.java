package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.contracts.IEmitter;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.GenItemStockThreadEndedEvent;
import com.danthy.pizzafun.app.events.GenOrderThreadEndedEvent;
import com.danthy.pizzafun.app.events.GenSupplierThreadEndedEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class GameThreadHandle extends IEmitter implements IListener {

    @Override
    public void update(IEvent event) {
        if (event.getClass() == GenItemStockThreadEndedEvent.class) {
            initGenItemStockThreadHandle();
        } else if (event.getClass() == GenOrderThreadEndedEvent.class) {
            new GenOrderHandle().handle();
        } else if (event.getClass() == GenSupplierThreadEndedEvent.class) {
            initGenSupplierThreadHandle();
        } else if (event.getClass() == StartGameEvent.class) {
            new GenOrderHandle().handle();
        }
    }

    public void initGenSupplierThreadHandle() {
        Task<Void> task = new GenSupplierThreadHandle(eventPublisher);

        task.setOnSucceeded((event) -> {
            eventPublisher.notifyAll(new GenSupplierThreadEndedEvent());
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void initGenItemStockThreadHandle() {
        Task<Void> task = new GenItemStockThreadHandle();

        task.setOnSucceeded((event) -> {
            eventPublisher.notifyAll(new GenItemStockThreadEndedEvent());
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
