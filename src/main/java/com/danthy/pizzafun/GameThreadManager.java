package com.danthy.pizzafun;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.StartGameEvent;

import java.util.ArrayList;
import java.util.List;

public class GameThreadManager implements IListener {
    private final List<Thread> threadList;

    private GameThreadManager() {
        this.threadList = new ArrayList<>();
    }

    public static GameThreadManager build() {
        return new GameThreadManager();
    }

    public GameThreadManager addThread(Thread thread) {
        this.threadList.add(thread);

        return this;
    }

    public void startAll() {
        this.threadList.forEach(Thread::start);
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            startAll();
        }
    }
}
