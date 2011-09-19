package com.porpoise.predicates;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
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

}
