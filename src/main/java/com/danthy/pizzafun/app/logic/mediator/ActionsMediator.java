package com.danthy.pizzafun.app.logic.mediator;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediator;

public class ActionsMediator implements IMediator {
    private final ControllerFacade controllerFacade;
    private final ServiceFacade serviceFacade;
    private final ManagerFacade managerFacade;
    private final FluxFacade fluxFacade;

    public ActionsMediator(ControllerFacade controllerFacade, ServiceFacade serviceFacade, ManagerFacade managerFacade, FluxFacade fluxFacade) {
        this.controllerFacade = controllerFacade;
        this.serviceFacade = serviceFacade;
        this.managerFacade = managerFacade;
        this.fluxFacade = fluxFacade;
    }

    @Override
    public void notify(IEvent event) {
        managerFacade.reactOn(event);
        serviceFacade.reactOn(event);
        controllerFacade.reactOn(event);
        fluxFacade.reactOn(event);
    }
}
