package com.ford.sumo.ordermanager.util;

import java.util.List;
import java.util.function.Supplier;

import static com.google.common.collect.Lists.newArrayList;

public class Selector<T> {

    private final T result;
    private List<T> results;
    private Boolean consumed;

    public Selector() {
        this.result = null;
        this.results = newArrayList();
    }

    private Selector(T result) {
        this.result = result;
    }

    private Selector(Boolean consumed) {
        this.result = null;
        this.consumed =consumed;
    }

    private Selector(List<T> results) {
        this.result = null;
        this.results = results;
    }

    public Selector<T> option(T result, Supplier<Boolean> condition) {

        if (this.result != null)
            return new Selector<>(this.result);

        if (condition.get())
            return new Selector<>(result);

        return new Selector<>();
    }

    public Selector<T> option(Supplier<T> result, Supplier<Boolean> condition) {

        if (this.result != null)
            return new Selector<>(this.result);

        if (condition.get())
            return new Selector<>(result.get());

        return new Selector<>();
    }

    public Selector<T> consume(Runnable result, Supplier<Boolean> condition) {

        if (condition.get() && !this.consumed) {
            result.run();
            this.consumed=true;
        }
        return new Selector<>();
    }

    public Selector<T> add(Supplier<T> result, Boolean condition) {

        if (this.results == null)
            this.results = newArrayList();

        if (condition && result !=null) {
                this.results.add(result.get());
        }

        return new Selector<>(results);
    }

    public List<T> collect() {
        return this.results != null && !this.results.isEmpty()
                ? results : newArrayList();
    }

    public T orElse(T result) {
        return this.result == null
                ? result
                : this.result;
    }

    public List<T> orElseGetList(List<T> defaultList) {
        return this.results != null
                ? this.results
                : defaultList;
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.result != null) {
            return result;
        } else {
            throw exceptionSupplier.get();
        }
    }
}

