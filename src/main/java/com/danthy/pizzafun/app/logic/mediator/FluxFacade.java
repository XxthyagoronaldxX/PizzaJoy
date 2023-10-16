package com.danthy.pizzafun.app.logic.mediator;

import com.danthy.pizzafun.app.contracts.IFlux;

import java.util.HashMap;
import java.util.Map;

public class FluxFacade {
    private final Map<Class, IFlux> fluxMap;

    private FluxFacade() {
        this.fluxMap = new HashMap<>();
    }

    public static FluxFacade build() {
        return new FluxFacade();
    }

    public FluxFacade addTimeHandle(IFlux flux) {
        this.fluxMap.put(flux.getClass(), flux);

        return this;
    }

    public <T extends IFlux> T getTimeHandle(Class clazz) {
        return (T) this.fluxMap.get(clazz);
    }
}
