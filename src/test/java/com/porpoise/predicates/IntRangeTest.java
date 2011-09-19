package com.porpoise.predicates;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class IntRangeTest {

	@Test
	public void testFillIn() {
		final List<Integer> filledIn = IntRange.fillIn(ImmutableList.of(2, 3),
				0, 5);
		ListAssertions.assertContent(ImmutableList.of(2, 3, 0, 1, 4, 5),
				filledIn);
	}

	@Test
	public void testFillInWithNegatives() {
		final List<Integer> filledIn = IntRange.fillIn(ImmutableList.of(0), -1,
				1);
		ListAssertions.assertContent(ImmutableList.of(0, -1, 1), filledIn);
	}

}
