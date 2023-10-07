package com.danthy.pizzafun.domain.data;

import com.danthy.pizzafun.app.utils.ApplicationProperties;
import com.danthy.pizzafun.domain.models.*;

public class PostConstruct {

    public static RoomModel genRoomModel(String name) {
        StockModel stockModel = new StockModel(150);

        ItemStockModel queijoStockModel = new ItemStockModel(
                new ItemModel("Queijo", 2),
                10
        );
        ItemStockModel massaStockModel = new ItemStockModel(
                new ItemModel("Massa", 4),
                10
        );
        ItemStockModel molhoDeTomateStockModel = new ItemStockModel(
                new ItemModel("Molho de tomate", 4),
                10
        );

        stockModel
                .addItemStockModel(queijoStockModel)
                .addItemStockModel(molhoDeTomateStockModel)
                .addItemStockModel(massaStockModel);

        RoomModel roomModel = new RoomModel(name, stockModel);

        roomModel.incBalance(100);

        return roomModel;
    }

    public static SupplierModel genSupplierModel() {
        return new SupplierModel(
                ApplicationProperties.roomInitialSupplierLevel,
                ApplicationProperties.roomInitialSupplierName,
                ApplicationProperties.roomInitialSupplierCost,
                ApplicationProperties.roomInitialSupplierBonus,
                ApplicationProperties.roomInitialSupplierBonusChance,
                ApplicationProperties.roomInitialSupplierDeliveryTimeInSeconds
        );
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
                .setPrice(32.0f)
                .setEdge(pizzaNoEdge)
                .addItemPizzaModel(massaItem, 1) //ERRO TA AQUI
                .addItemPizzaModel(queijoItem, 2)
                .addItemPizzaModel(molhoDeTomateItem, 1)
                .addItemPizzaModel(calabresaItem, 3);

        PizzaModel quatroQueijosPizza = PizzaModel
                .build()
                .setName("Pizza de Quatro queijos")
                .setPrice(40.0f)
                .setEdge(pizzaNoEdge)
                .addItemPizzaModel(massaItem, 1)
                .addItemPizzaModel(molhoDeTomateItem, 1)
                .addItemPizzaModel(queijoItem, 4);

        PizzaModel florestaNegraPizza = PizzaModel
                .build()
                .setName("Pizza Floresta Negra")
                .setPrice(45.0f)
                .setEdge(pizzaNoEdge)
                .addItemPizzaModel(chocolateItem, 3);

        PizzaDataSingleton
                .getInstance()
                .addAllItem(calabresaPizza, quatroQueijosPizza, florestaNegraPizza);
    }
}
