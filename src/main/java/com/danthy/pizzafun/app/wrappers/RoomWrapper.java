package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapperModel;
import com.danthy.pizzafun.domain.models.RoomModel;

public class RoomWrapper implements IWrapperModel<RoomModel> {
    private final RoomModel roomModel;

    public RoomWrapper(RoomModel roomModel) {
        this.roomModel = roomModel;
    }

    @Override
    public RoomModel getWrapped() {
        return this.roomModel;
    }
}
