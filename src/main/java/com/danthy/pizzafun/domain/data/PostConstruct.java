package com.danthy.pizzafun.domain.data;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.domain.models.*;

import java.util.UUID;

public class PostConstruct {

    public static RoomModel genRoomModel(String name) {
        StockModel stockModel = new StockModel(150);

        ItemStockModel queijoStockModel = new ItemStockModel(
                new ItemModel("Queijo", 2),
                80
        );
        ItemStockModel massaStockModel = new ItemStockModel(
                new ItemModel("Massa", 4),
                40
        );
        ItemStockModel molhoDeTomateStockModel = new ItemStockModel(
                new ItemModel("Molho de tomate", 4),
                40
        );

        stockModel
                .addItemStockModel(queijoStockModel)
                .addItemStockModel(molhoDeTomateStockModel)
                .addItemStockModel(massaStockModel);

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

        roomModel.setSupplierModel(supplierModel);
        roomModel.setStockModel(stockModel);
        roomModel.setBalance(ApplicationProperties.roomInitialBalance);
        roomModel.setTokens(ApplicationProperties.roomInitialTokens);

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
