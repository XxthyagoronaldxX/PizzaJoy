package com.danthy.pizzafun.app.config;

import com.danthy.pizzafun.app.utils.PathUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {
    public static double pizzaProduceBaseLevelUp;
    public static int pizzaGenerationMinBaseTime;
    public static int pizzaGenerationMaxBaseTime;
    public static int itemMaxWeight;
    public static int roomInitialMaxSuppliers;
    public static String[] supplierNames;
    public static int roomAutoSaveTimeInSeconds;
    public static int supplierGenerationBasetime;


    public ApplicationProperties() {}

    public static void init() {
        Properties properties = new Properties();

        loadProperties(properties);
        setProperties(properties);
    }

    private static void setProperties(Properties properties) {
        pizzaProduceBaseLevelUp = Double.parseDouble((String) properties.get("pizza.produce.baselevelup"));
        pizzaGenerationMaxBaseTime = Integer.parseInt((String) properties.get("pizza.generation.maxbasetime"));
        pizzaGenerationMinBaseTime = Integer.parseInt((String) properties.get("pizza.generation.minbasetime"));
        roomInitialMaxSuppliers = Integer.parseInt((String) properties.get("room.initial.maxsuppliers"));
        supplierNames = ((String) properties.get("supplier.names")).split(";");
        supplierGenerationBasetime = Integer.parseInt((String) properties.get("supplier.generation.basetime"));

        roomAutoSaveTimeInSeconds = Integer.parseInt((String) properties.get("room.autoSave.timeInSeconds"));
        itemMaxWeight = Integer.parseInt(properties.getProperty("item.maxweight"));
    }

    private static void loadProperties(Properties properties) {
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
    }
}
