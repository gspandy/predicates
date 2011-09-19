package com.porpoise.predicates;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.primitives.Ints;
import com.porpoise.predicates.StatisticFunction.StatisticResult;

/**
 * A type of {@link Predicate}, but which returns a result which knows which
 * underlying predicate caused the result to fail (or succeed)
 * 
 * @param <T>
 */
public class StatisticFunction<T> implements Function<T, StatisticResult> {

	private final List<Predicate<T>> predicates;
	private final int size;

	/**
	 */
	public static class StatisticResult implements Comparable<StatisticResult> {
		public static final Function<StatisticResult, Integer> AS_INDEX = new Function<StatisticFunction.StatisticResult, Integer>() {
			@Override
			public Integer apply(final StatisticResult input) {
				return Integer.valueOf(input.index);
			}
		};
		private final boolean result;
		private final int index;
		private final int total;

		private StatisticResult(final boolean value, final int index,
				final int totalPredicates) {
			this.result = value;
			this.index = index;
			this.total = totalPredicates;
		}

		public static StatisticResult fail(final int i, final int total) {
			return new StatisticResult(false, i, total);
		}

		public static StatisticResult success(final int total) {
			return new StatisticResult(true, total - 1, total);
		}

		@Override
		public String toString() {
			return String.format("%s [%s/%s]", this.result, this.index,
					this.total);
		}

		@Override
		public int compareTo(final StatisticResult other) {
			assert other.total == this.total : "statistic results should only be compared with other results from the same predicate list";
			return Ints.compare(this.index, other.index);
		}

		public boolean getResult() {
			return this.result;
		}
	}

	public StatisticFunction(final List<Predicate<T>> predicates) {
		this.predicates = Preconditions.checkNotNull(predicates);
		this.size = predicates.size();
	}

	@Override
	public StatisticResult apply(final T input) {
		int i = 0;
		for (final Predicate<T> test : this.predicates) {
			if (!test.apply(input)) {
				return StatisticResult.fail(i, this.size);
			}
			i++;
		}
		return StatisticResult.success(this.size);
	}

	/**
	 * @return the number of test predicates
	 */
	int size() {
		return this.predicates.size();
	}

	StatisticFunction<T> reorder(final List<StatisticResult> copy) {
		final Collection<Integer> firstFailingIndices = Collections2.transform(
				copy, StatisticResult.AS_INDEX);
		final List<Integer> sortedIndices = IntRange.fillIn(
				firstFailingIndices, 0, size());
		final List<Predicate<T>> reorderedPredicates = ListReorder.reorder(
				this.predicates, sortedIndices);
		return new StatisticFunction<T>(reorderedPredicates);
	}

}
