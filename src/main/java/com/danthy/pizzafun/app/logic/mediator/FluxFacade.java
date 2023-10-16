package com.danthy.pizzafun.app.logic.mediator;

import com.danthy.pizzafun.app.contracts.Flux;

import java.util.HashMap;
import java.util.Map;

public class FluxFacade {
    private final Map<Class, Flux> fluxMap;

    private FluxFacade() {
        this.fluxMap = new HashMap<>();
    }

    public static FluxFacade build() {
        return new FluxFacade();
    }

    public FluxFacade addFlux(Flux flux) {
        this.fluxMap.put(flux.getClass(), flux);

        return this;
    }

    public <T extends Flux> T getFlux(Class clazz) {
        return (T) this.fluxMap.get(clazz);
    }
}
