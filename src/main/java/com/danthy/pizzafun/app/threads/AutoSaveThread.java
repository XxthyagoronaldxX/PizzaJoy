package com.danthy.pizzafun.app.threads;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.contracts.IHandle;
import com.danthy.pizzafun.app.events.SaveSnapshotRoomEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.utils.TimelineUtil;

public class AutoSaveThread implements IHandle {
    public EventPublisher eventPublisher;

    public AutoSaveThread() {
        eventPublisher = GetIt.getInstance().find(EventPublisher.class);
    }

    @Override
    public void handle() {
        TimelineUtil.runFunctionAfterTimeInSeconds(ApplicationProperties.roomAutoSaveTimeInSeconds, (event) -> {
            eventPublisher.notifyAll(new SaveSnapshotRoomEvent());
        });
    }
}
