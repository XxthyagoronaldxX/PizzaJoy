package com.danthy.pizzafun.app.logic.mediator;

import com.danthy.pizzafun.app.contracts.Flux;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IFacadeMediator;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FluxFacade implements IFacadeMediator<Flux> {
    private final Map<IEvent, > fluxMap;

    private FluxFacade() {
        this.fluxMap = new HashMap<>();
    }

    public static FluxFacade build() {
        return new FluxFacade();
    }

    public FluxFacade addFlux(Flux flux) {
        Arrays.stream(flux.getClass().getDeclaredMethods()).forEach(System.out::println);

        this.fluxMap.put(flux.getClass(),);

        return this;
    }

    @Override
    public void reactOn(IEvent event) {

    }

    @Override
    public IFacadeMediator add(Flux flux) {
        Class<?> clazz = flux.getClass();

        if (fluxMap.get(clazz).isEmpty()) {
            Map<Flux, Map<>>

        }

        return null;
    }
}
