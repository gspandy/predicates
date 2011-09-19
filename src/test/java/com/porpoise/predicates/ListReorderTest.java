package com.porpoise.predicates;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class ListReorderTest {

	@Test
	public void testReorder() {
		final List<Character> reordered = ListReorder.reorder(
				ImmutableList.of('a', 'b', 'c'), ImmutableList.of(1, 2, 0));
		ListAssertions
				.assertContent(ImmutableList.of('b', 'c', 'a'), reordered);
	}

}
