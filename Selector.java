package com.ford.sumo.util;

import java.util.function.Supplier;

public class Selector<T> {

    public final T result;

    public Selector() {
	this.result = null;
    }

    public Selector(T result) {
	this.result = result;
    }

    public Selector<T> option(T result, Supplier<Boolean> condition) {

	if (this.result != null)
	    return new Selector<>(this.result);

	if (condition.get())
	    return new Selector<>(result);

	return new Selector<>();
    }

    public T orElse(T result) {
	return this.result == null //
		? result//
		: this.result;
    }

	public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
		if (this.result != null) {
			return result;
		} else {
			throw exceptionSupplier.get();
		}
	}
}
