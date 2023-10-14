package com.danthy.pizzafun.app.config;

import com.danthy.pizzafun.app.utils.PathUtil;
import com.danthy.pizzafun.domain.enums.SupplierLevel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class ApplicationProperties {
    public static double pizzaProduceBaseLevelUp;
    public static int pizzaGenerationMinBaseTime;
    public static int pizzaGenerationMaxBaseTime;
    public static int itemMaxWeight;
    public static int roomInitialMaxPizzas;
    public static int roomInitialMaxSuppliers;
    public static double roomInitialBalance;
    public static int roomInitialTokens;
    public static String[] supplierNames;
    public static String[] roomInitialStockItems;
    public static int roomInitialStockAvaible;
    public static int supplierGenerationBasetime;
    public static String roomInitialSupplierName;
    public static double roomInitialSupplierCost;
    public static int roomInitialSupplierBonus;
    public static double roomInitialSupplierBonusChance;
    public static double roomInitialSupplierDeliveryTimeInSeconds;
    public static SupplierLevel roomInitialSupplierLevel;


    public ApplicationProperties() {}

    public static void init() {
        Properties properties = new Properties();

        try {
            String rootPath = PathUtil.getRootPath() +
                    File.separator +
                    "java" +
                    File.separator +
                    "application.properties";

            properties.load(new FileInputStream(rootPath));
        } catch (IOException e) {
            System.out.println("File Error: Error when trying to load Application Properties.");
            throw new RuntimeException(e);
        }

        pizzaProduceBaseLevelUp = Double.parseDouble((String) properties.get("pizza.produce.baselevelup"));
        pizzaGenerationMaxBaseTime = Integer.parseInt((String) properties.get("pizza.generation.maxbasetime"));
        pizzaGenerationMinBaseTime = Integer.parseInt((String) properties.get("pizza.generation.minbasetime"));
        roomInitialMaxPizzas = Integer.parseInt((String) properties.get("room.initial.maxpizzas"));
        roomInitialMaxSuppliers = Integer.parseInt((String) properties.get("room.initial.maxsuppliers"));
        roomInitialTokens = Integer.parseInt((String) properties.get("room.initial.tokens"));
        roomInitialBalance = Double.parseDouble((String) properties.get("room.initial.balance"));
        supplierNames = ((String) properties.get("supplier.names")).split(";");
        supplierGenerationBasetime = Integer.parseInt((String) properties.get("supplier.generation.basetime"));

        roomInitialStockItems = ((String) properties.get("room.initial.stock.items")).split("[|]");
        roomInitialStockAvaible = Integer.parseInt((String) properties.get("room.initial.stock.avaible"));
        roomInitialSupplierDeliveryTimeInSeconds = Double.parseDouble((String) properties.get("room.initial.supplier.deliveryTimeInSeconds"));
        roomInitialSupplierBonus = Integer.parseInt((String) properties.get("room.initial.supplier.bonus"));
        roomInitialSupplierBonusChance = Double.parseDouble((String) properties.get("room.initial.supplier.bonusChance"));
        roomInitialSupplierCost = Integer.parseInt((String) properties.get("room.initial.supplier.cost"));
        roomInitialSupplierName = properties.getProperty("room.initial.supplier.name");
        roomInitialSupplierLevel = SupplierLevel.valueOf(properties.getProperty("room.initial.supplier.level"));
        itemMaxWeight = Integer.parseInt(properties.getProperty("item.maxweight"));
    }
}
