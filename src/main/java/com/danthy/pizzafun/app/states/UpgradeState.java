package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.domain.models.RoomModel;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class UpgradeState {
    private final ObservableList<UpgradeModel> upgradeModelObservableList;

    public UpgradeState(RoomModel roomModel) {
        upgradeModelObservableList = FXCollections.observableList(roomModel.getUpgradeModelList());
    }
}
