package com.danthy.pizzafun.domain.data;

import com.danthy.pizzafun.app.utils.PathUtil;
import com.danthy.pizzafun.domain.models.ItemModel;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemDataSingleton {

    private static final ItemDataSingleton I = new ItemDataSingleton();
    private final List<ItemModel> itemModels;

    public ItemDataSingleton() {
        this.itemModels = new ArrayList<>();
    }

    public static ItemDataSingleton getInstance() {
        return I;
    }

    public List<ItemModel> getItemModels() {
        return itemModels;
    }

    public void addItem(ItemModel itemModel) {
        this.itemModels.add(itemModel);
    }

    public void addAllItem(ItemModel... itemModels) {
        this.itemModels.addAll(Arrays.asList(itemModels));
    }
}
