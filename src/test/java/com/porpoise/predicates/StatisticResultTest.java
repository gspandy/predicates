package com.porpoise.predicates;

import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.porpoise.predicates.StatisticFunction.StatisticResult;

public class StatisticResultTest {

	/**
	 */
	@Test
	public void testCompareStatisticResults() {
		Assert.assertEquals(
				0,
				StatisticResult.success(10).compareTo(
						StatisticResult.success(10)));

		Assert.assertEquals(
				"failing earlier should be prefered to failing later",
				-1,
				StatisticResult.fail(0, 10).compareTo(
						StatisticResult.fail(1, 10)));

		// assert we can sort - failing earlier should be preferred
		final StatisticResult last = StatisticResult.fail(10, 10);
		final StatisticResult third = StatisticResult.fail(5, 10);
		final StatisticResult first = StatisticResult.fail(2, 10);
		final StatisticResult second = StatisticResult.fail(4, 10);
		final List<StatisticResult> results = Lists.newArrayList(second, first,
				last, third);

		Collections.sort(results);

		Assert.assertSame(first, results.get(0));
		Assert.assertSame(second, results.get(1));
		Assert.assertSame(third, results.get(2));
		Assert.assertSame(last, results.get(3));

	}

}
