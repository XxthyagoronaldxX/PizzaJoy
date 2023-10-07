package com.danthy.pizzafun;

import java.util.ArrayList;
import java.util.List;

public class GameThreadManager {
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

    public void stopAll() {
        this.threadList.forEach(Thread::interrupt);
    }
}
