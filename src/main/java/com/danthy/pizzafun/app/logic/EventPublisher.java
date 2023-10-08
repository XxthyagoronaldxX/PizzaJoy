package com.danthy.pizzafun.app.logic;

import java.util.ArrayList;
import java.util.List;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;

public class EventPublisher {

    private final List<IListener> listeners;

    public EventPublisher() {
        this.listeners = new ArrayList<>();
    }

    public EventPublisher addListener(IListener listener) {
        this.listeners.add(listener);

        return this;
    }

    public void removeListener(IListener listener) {
        this.listeners.remove(listener);
    }

    public void notifyAll(IEvent event) {
        listeners.forEach(listener -> listener.update(event));
    }
}
