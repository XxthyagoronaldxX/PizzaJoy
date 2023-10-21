package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.domain.enums.UpgradeType;
import com.danthy.pizzafun.domain.models.RoomModel;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UpgradeState {
    private final ObservableList<UpgradeModel> upgradeModelNotifierList;

    private final ObservableValue<UpgradeModel> upgradePizzariaNotifierValue;

    public UpgradeState(RoomModel roomModel) {
        List<UpgradeModel> upgradeModelListFiltered = new ArrayList<>();

        UpgradeModel pizzariaUpgradeModel = null;
        for (UpgradeModel upgradeModel : roomModel.getUpgradeModelList()) {
            if (upgradeModel.getUpgradeType() != UpgradeType.PIZZARIA)
                upgradeModelListFiltered.add(upgradeModel);
            else
                pizzariaUpgradeModel = upgradeModel;
        }

        upgradePizzariaNotifierValue = new ObservableValue<>(pizzariaUpgradeModel);
        upgradeModelNotifierList = FXCollections.observableList(upgradeModelListFiltered);
    }
}
