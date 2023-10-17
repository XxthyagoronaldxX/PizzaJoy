package com.danthy.pizzafun.app.events.mediator;

import com.danthy.pizzafun.app.contracts.IEvent;

public record StartGameEvent(String roomName) implements IEvent { }
