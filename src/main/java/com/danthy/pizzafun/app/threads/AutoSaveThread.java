package com.danthy.pizzafun.app.threads;

import com.danthy.pizzafun.app.contracts.IHandle;
import com.danthy.pizzafun.app.events.SaveSnapshotRoomEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.utils.TimelineUtil;

public class AutoSaveThread implements IHandle {
    public static final int AUTO_SAVE_TIME = 5;
    public EventPublisher eventPublisher;

    public AutoSaveThread() {
        eventPublisher = GetIt.getInstance().find(EventPublisher.class);
    }

    @Override
    public void handle() {
        TimelineUtil.runFunctionAfterTimeInSeconds(AUTO_SAVE_TIME, (event) -> {
            eventPublisher.notifyAll(new SaveSnapshotRoomEvent());
        });
    }
}
