package com.danthy.pizzafun.domain.models;

import java.util.UUID;

public class OrderModel {

    private UUID id;
    private NpcModel npcModel;
    private PizzaModel pizzaModel;

    public OrderModel(NpcModel npcModel, PizzaModel pizzaModel) {
        this.id = UUID.randomUUID();
        this.npcModel = npcModel;
        this.pizzaModel = pizzaModel;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public NpcModel getNpcModel() {
        return npcModel;
    }

    public void setNpcModel(NpcModel npcModel) {
        this.npcModel = npcModel;
    }

    public PizzaModel getPizzaModel() {
        return pizzaModel;
    }

    public void setPizzaModel(PizzaModel pizzaModel) {
        this.pizzaModel = pizzaModel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OrderModel other = (OrderModel) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Override
    public String toString() {
        return this.pizzaModel.getItemPizzaModels()
                .stream()
                .map(item
                        -> item.getItem().getName() + " [" + item.getQuantity() + "x]\n"
                )
                .reduce("", (acc, sItem) -> acc + sItem);
    }
}
