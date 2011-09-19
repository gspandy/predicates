package com.porpoise.predicates;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class IntRange {

	private IntRange() {
		super();
	}

	/**
	 * <p>
	 * Given some integers in iteration order, return a list of integers with
	 * min (inclusive) and max (inclusive), return a list of all integers
	 * between min (inclusive ) and max (inclusive) whos first integers are
	 * those given, and the remaining integers are the remaining numbers within
	 * the range.
	 * </p>
	 * for example, given a min of 10 and a max of 14 with initial integers 11
	 * and 13, the list of elements (11,13,10,12) will be returned
	 * 
	 * @param integers
	 *            unique collection of integers. This cannot realistically be a
	 *            set (or sorted set), so the uniqueness must be guaranteed by
	 *            the caller
	 * @param size
	 * @return a filled in list of the expected size
	 */
	public static List<Integer> fillIn(final Collection<Integer> integers,
			final int minInclusive, final int maxInclusive) {
		final int diff = maxInclusive - minInclusive;
		assert diff >= 0;
		assert Sets.newHashSet(integers).size() != integers.size() : "The integers were NOT unique";
		final List<Integer> filledIn = Lists.newArrayList(integers);

		for (int i = minInclusive; i <= maxInclusive; i++) {
			if (!filledIn.contains(i)) {
				filledIn.add(Integer.valueOf(i));
			}
		}

		return filledIn;
	}
}
