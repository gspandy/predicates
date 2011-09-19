package com.porpoise.predicates;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.porpoise.predicates.StatisticFunction.StatisticResult;

public class StatisticPredicate<T> implements Predicate<T> {

	private StatisticFunction<T> testFunction;

	private final Collection<StatisticResult> results = Lists
			.newArrayListWithExpectedSize(100);

	public StatisticPredicate(final Predicate<T>... tests) {
		this(Arrays.asList(tests));
	}

	public StatisticPredicate(final List<Predicate<T>> tests) {
		this(new StatisticFunction<T>(tests));
	}

	private StatisticPredicate(final StatisticFunction<T> f) {
		this.testFunction = f;
	}

	@Override
	public boolean apply(final T input) {
		final StatisticResult result = this.testFunction.apply(input);
		this.results.add(result);
		return result.getResult();
	}

	public StatisticPredicate<T> reorder() {
		final List<StatisticResult> copy = Lists.newArrayList(this.results);
		Collections.sort(copy);
		return new StatisticPredicate<T>(this.testFunction.reorder(copy));
	}
}
