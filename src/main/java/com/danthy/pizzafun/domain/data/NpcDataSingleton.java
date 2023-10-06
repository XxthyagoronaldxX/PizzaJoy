package com.danthy.pizzafun.domain.data;


import com.danthy.pizzafun.domain.models.NpcModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NpcDataSingleton {
    private static final NpcDataSingleton I = new NpcDataSingleton();
    private final List<NpcModel> npcModels;

    public NpcDataSingleton() {
        this.npcModels = new ArrayList<>();
    }

    public static NpcDataSingleton getInstance() {
        return NpcDataSingleton.I;
    }

    public List<NpcModel> getNpcModels() {
        return npcModels;
    }

    public NpcModel getRandomNpc() {
        return npcModels.get((int) Math.floor(Math.random() * (npcModels.size() - 1)));
    }

    public void addItem(NpcModel item) {
        this.npcModels.add(item);
    }

    public void addAllItem(NpcModel... itemModels) {
        this.npcModels.addAll(Arrays.asList(itemModels));
    }
}
