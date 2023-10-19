package com.danthy.pizzafun.newFeature;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MediatorTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public List<Class<?>> getClasses(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace(".", "/");
        String auxPath = packageName.replace(".", File.separator);

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File directory = new File(resource.getFile());

                if (directory.exists()) {
                    List<String> classPaths = Files.walk(directory.toPath()).filter(Files::isRegularFile).map(Path::toString).filter(pathString -> pathString.endsWith(".class")).toList();

                    List<? extends Class<?>> classLoadedList = classPaths.stream()
                            .map(classPath -> classPath.substring(classPath.indexOf(auxPath)).replace("\\", "."))
                            .map(classPath -> {
                                try {
                                    Class<?> clazz =getClass().getClassLoader().loadClass(classPath);
                                    System.out.println(classPath);


                                    return clazz;
                                } catch (ClassNotFoundException e) {
                                    return new MediatorTest().getClass();
                                }
                            })
                            .toList();

                    for (Class<?> clazz : classLoadedList) {
                        //System.out.println(clazz.getName());
                    }



                    //Class.forName();

                    /*File[] files = directory.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (file.isFile() && file.getName().endsWith(".class") && !file.getName().equals("module-info.class")) {
                                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                                try {
                                    Class<?> clazz = Class.forName(className);
                                    classes.add(clazz);
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }*/
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return classes;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ClassFinder.main(null);
    }
}
