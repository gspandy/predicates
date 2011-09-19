package com.porpoise.predicates;

import java.util.concurrent.atomic.AtomicReference;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

public class PredicateDelegate<T> implements Predicate<T> {

	private final AtomicReference<Predicate<T>> ref = new AtomicReference<Predicate<T>>();

	public PredicateDelegate(final Predicate<T> underlying) {
		this.ref.set(Preconditions.checkNotNull(underlying));
	}

	public Predicate<T> replace(final Predicate<T> underlying) {
		return this.ref.getAndSet(Preconditions.checkNotNull(underlying));
	}

	@Override
	public boolean apply(final T input) {
		return this.ref.get().apply(input);
	}

}
