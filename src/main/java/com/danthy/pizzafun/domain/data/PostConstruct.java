package com.danthy.pizzafun.domain.data;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.domain.models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PostConstruct {

    public static RoomModel genRoomModel(String name) {
        ItemModel queijoItem = new ItemModel("Queijo", 2);
        ItemModel calabresaItem = new ItemModel("Calabresa", 2);
        ItemModel massaItem = new ItemModel("Massa", 4);
        ItemModel molhoDeTomateItem = new ItemModel("Molho de tomate", 4);

        ItemStockModel queijoStockModel = new ItemStockModel(queijoItem, 80);
        ItemStockModel massaStockModel = new ItemStockModel(massaItem, 40);
        ItemStockModel molhoDeTomateStockModel = new ItemStockModel(molhoDeTomateItem, 40);
        ItemStockModel calabresaStockModel = new ItemStockModel(calabresaItem, 40);

        PizzaEdgeModel pizzaNoEdge = PizzaEdgeModel.build();

        StockModel stockModel = new StockModel(150);
        stockModel
                .addItemStockModel(queijoStockModel)
                .addItemStockModel(molhoDeTomateStockModel)
                .addItemStockModel(massaStockModel)
                .addItemStockModel(calabresaStockModel);

        PizzaModel calabresaPizza = PizzaModel
                .build()
                .setName("Pizza de Calabresa")
                .setPriceToSell(32.0f)
                .setPriceToBuyRecipe(0f)
                .setEdge(pizzaNoEdge)
                .addItemPizzaModel(massaItem, 1)
                .addItemPizzaModel(queijoItem, 2)
                .addItemPizzaModel(molhoDeTomateItem, 1)
                .addItemPizzaModel(calabresaItem, 3);

        PizzaModel quatroQueijosPizza = PizzaModel
                .build()
                .setName("Pizza de Quatro queijos")
                .setPriceToSell(40.0f)
                .setPriceToBuyRecipe(0f)
                .setEdge(pizzaNoEdge)
                .addItemPizzaModel(massaItem, 1)
                .addItemPizzaModel(molhoDeTomateItem, 1)
                .addItemPizzaModel(queijoItem, 4);

        List<PizzaModel> pizzaModelList = Arrays.asList(calabresaPizza, quatroQueijosPizza);

        SupplierModel supplierModel = SupplierModel
                .builder()
                .id(UUID.randomUUID())
                .deliveryTimeInSeconds(ApplicationProperties.roomInitialSupplierDeliveryTimeInSeconds)
                .supplierLevel(ApplicationProperties.roomInitialSupplierLevel)
                .name(ApplicationProperties.roomInitialSupplierName)
                .cost(ApplicationProperties.roomInitialSupplierCost)
                .bonus(ApplicationProperties.roomInitialSupplierBonus)
                .bonusChance(ApplicationProperties.roomInitialSupplierBonusChance)
                .build();

        RoomModel roomModel = new RoomModel(name);

        UpgradeModel cookUpgradeModel = UpgradeModel
                .build()
                .setLevel(0)
                .setName("Cozinheiro")
                .setUpgradeCostBase(120)
                .setUpgradeCostScale(1.2)
                .setUpgradeCost(120);

        List<UpgradeModel> upgradeModelList = Arrays.asList(cookUpgradeModel);

        roomModel.setSupplierModel(supplierModel);
        roomModel.setUpgradeModelList(upgradeModelList);
        roomModel.setStockModel(stockModel);
        roomModel.setBalance(ApplicationProperties.roomInitialBalance);
        roomModel.setTokens(ApplicationProperties.roomInitialTokens);
        roomModel.setPizzaModels(pizzaModelList);

        return roomModel;
    }

    public static void genModels() {
        // BUILDING STANDARD ITEMS
        ItemModel queijoItem = new ItemModel("Queijo", 2);
        ItemModel calabresaItem = new ItemModel("Calabresa", 2);
        ItemModel massaItem = new ItemModel("Massa", 4);
        ItemModel molhoDeTomateItem = new ItemModel("Molho de tomate", 4);
        ItemModel chocolateItem = new ItemModel("Chocolate", 3);

        ItemDataSingleton
                .getInstance()
                .addAllItem(
                        queijoItem,
                        calabresaItem,
                        massaItem,
                        molhoDeTomateItem,
                        chocolateItem
                );

        // BUILDING STANDARD EDGES
        PizzaEdgeModel pizzaNoEdge = PizzaEdgeModel.build();

        PizzaEdgeDataSingleton.getInstance().addAllItem(pizzaNoEdge);

        //BUILDING STANDARD PIZZAS
        PizzaModel calabresaPizza = PizzaModel
                .build()
                .setName("Pizza de Calabresa")
                .setPriceToSell(32.0f)
                .setPriceToBuyRecipe(0f)
                .setTimeInSecondsToLearn(0)
                .setEdge(pizzaNoEdge)
                .addItemPizzaModel(massaItem, 1)
                .addItemPizzaModel(queijoItem, 2)
                .addItemPizzaModel(molhoDeTomateItem, 1)
                .addItemPizzaModel(calabresaItem, 3);

        PizzaModel quatroQueijosPizza = PizzaModel
                .build()
                .setName("Pizza de Quatro queijos")
                .setPriceToSell(40.0f)
                .setPriceToBuyRecipe(0f)
                .setTimeInSecondsToLearn(0)
                .setEdge(pizzaNoEdge)
                .addItemPizzaModel(massaItem, 1)
                .addItemPizzaModel(molhoDeTomateItem, 1)
                .addItemPizzaModel(queijoItem, 4);

        PizzaModel florestaNegraPizza = PizzaModel
                .build()
                .setName("Pizza Floresta Negra")
                .setPriceToSell(58.0f)
                .setPriceToBuyRecipe(15)
                .setTimeInSecondsToLearn(260)
                .setEdge(pizzaNoEdge)
                .addItemPizzaModel(massaItem, 1)
                .addItemPizzaModel(chocolateItem, 3);

        PizzaDataSingleton
                .getInstance()
                .addAllItem(calabresaPizza, quatroQueijosPizza, florestaNegraPizza);
    }
}
