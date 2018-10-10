package com.ford.sumo.ordermanager.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class Accumlator<T> {


    public final T result;
    public List<T> results;

    public Accumlator() {
        this.result = null;
        this.results = newArrayList();
    }

    public Accumlator(T result) {
        this.result = result;
    }

    public Accumlator(List<T> results) {
        this.result = null;
        this.results = results;
    }

    public Accumlator<T> consume(Consumer<T> consumer, Boolean condition) {
        if (condition) {
            consumer.accept(result);
        }
        return new Accumlator<>(result);
    }

    public Accumlator<T> consumeList(Consumer<List> consumer, Boolean condition) {
        if (condition) {
            consumer.accept(results);
        }
        return new Accumlator<>(results);
    }

    public List<T> finalResult() {
            return results;
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.result != null) {
            return result;
        } else {
            throw exceptionSupplier.get();
        }
    }
}
