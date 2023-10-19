package com.danthy.pizzafun.newFeature;

import com.danthy.pizzafun.app.contracts.ReactOn;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassFinder {

    public static void main(String[] args) {
        List<Class<?>> classesWithReactOn = findClassesWithAnnotation("com.danthy.pizzafun", ReactOn.class);

        System.out.println(classesWithReactOn);

        for (Class<?> clazz : classesWithReactOn) {
            System.out.println("Classe com @ReactOn: " + clazz.getName());
        }
    }

    public static List<Class<?>> findClassesWithAnnotation(String basePackage, Class<? extends Annotation> annotation) {
        List<Class<?>> classesWithAnnotation = new ArrayList<>();
        List<Class<?>> allClasses = getClassesInPackage(basePackage);

        for (Class<?> clazz : allClasses) {
            if (clazz.isAnnotationPresent(annotation)) {
                classesWithAnnotation.add(clazz);
            }
        }

        return classesWithAnnotation;
    }

    public static List<Class<?>> getClassesInPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            Enumeration<URL> resources = classLoader.getResources(packagePath);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("file")) {
                    File directory = new File(resource.getFile());
                    classes.addAll(findClassesInDirectory(packageName, directory));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return classes;
    }

    public static List<Class<?>> findClassesInDirectory(String packageName, File directory) {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClassesInDirectory(packageName + "." + file.getName(), file));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return classes;
    }
}
