package com.danthy.pizzafun.app.events;

import com.danthy.pizzafun.app.contracts.IEvent;

public record StartGameEvent(String roomName) implements IEvent { }
