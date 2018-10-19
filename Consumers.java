package com.ford.sumo.ordermanager.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Consumers<T> {

    private Boolean consumed;
    private T value;

    public Consumers(){}

    private Consumers(Boolean consumed) {
        this.consumed =consumed;
    }

    public Consumers<T> consume(Consumer<T> consumer, Supplier<Boolean> condition) {
        if (condition.get()) {
            consumer.accept(value);
            new Consumers<>(true);
        }
        return new Consumers<>();
    }

    public Boolean isConsumed(){
        return this.consumed;
    }
}
