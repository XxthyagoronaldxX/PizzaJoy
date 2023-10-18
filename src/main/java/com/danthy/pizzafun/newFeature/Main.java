package com.danthy.pizzafun.newFeature;

public class Main {
    public static void main(String[] args) {
        TestService testService = new TestService();

        testService.update(new ConcreteTest());
    }
}
