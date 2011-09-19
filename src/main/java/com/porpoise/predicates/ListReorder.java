package com.porpoise.predicates;

import java.util.List;

import com.google.common.collect.Lists;

public class ListReorder {
	private ListReorder() {
		super();
	}

	public static <T> List<T> reorder(final List<T> things,
			final List<Integer> order) {
		if (things.size() != order.size()) {
			throw new IllegalArgumentException(String.format(
					"actual list size %s !=  ordered size %s", things.size(),
					order.size()));
		}

		final List<T> reordered = Lists.newArrayList(things);
		int index = 0;
		for (final Integer i : order) {
			final int targetIndex = i.intValue();
			final T value = things.get(targetIndex);
			reordered.set(index++, value);
		}

		return reordered;
	}

}
