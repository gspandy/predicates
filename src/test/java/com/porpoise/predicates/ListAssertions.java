package com.porpoise.predicates;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;

public class ListAssertions {
	private ListAssertions() {
		super();
	}

	public static <T> void assertContent(final ImmutableList<T> expected,
			final List<T> list) {
		Assert.assertEquals(expected.size(), list.size());
		final UnmodifiableIterator<T> expectedIter = expected.iterator();
		final Iterator<T> actualIter = list.iterator();
		while (expectedIter.hasNext()) {
			Assert.assertEquals(expectedIter.next(), actualIter.next());
		}

	}
}
