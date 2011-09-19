package com.porpoise.predicates;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Predicates;

public class PredicateDelegateTest {

	/**
	 */
	@Test
	public void testReplace() {
		final PredicateDelegate<String> predicate = new PredicateDelegate<String>(
				Predicates.<String> alwaysTrue());
		Assert.assertTrue(predicate.apply(null));
		// call the method under test
		predicate.replace(Predicates.<String> alwaysFalse());
		Assert.assertFalse(predicate.apply(null));
	}

}
