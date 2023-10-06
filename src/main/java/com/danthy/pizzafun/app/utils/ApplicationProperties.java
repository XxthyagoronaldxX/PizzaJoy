package com.danthy.pizzafun.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class ApplicationProperties {
    public static int pizzaProduceBaseTime;
    public static int pizzaGenerationMinBaseTime;
    public static int pizzaGenerationMaxBaseTime;
    public static int roomInitialMaxPizzas;

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

        pizzaProduceBaseTime = Integer.parseInt((String) properties.get("pizza.produce.basetime"));
        pizzaGenerationMaxBaseTime = Integer.parseInt((String) properties.get("pizza.generation.maxbasetime"));
        pizzaGenerationMinBaseTime = Integer.parseInt((String) properties.get("pizza.generation.minbasetime"));
        roomInitialMaxPizzas = Integer.parseInt((String) properties.get("room.initial.maxpizzas"));
    }
}
