package com.mateuszmidor.data;

import junit.framework.Assert;

import org.junit.Test;

import com.mateuszmidor.data.SymbolNamePair;

public class SymbolNamePairTest {

	@Test
	public void testGetSymbol() {
		// check the class enforces symbol always uppercase policy
		SymbolNamePair pair = new SymbolNamePair("eur", "euro");
		Assert.assertEquals("EUR", pair.getSymbol());
	}

	@Test
	public void testGetName() {
		SymbolNamePair pair = new SymbolNamePair("eur", "euro");
		Assert.assertEquals("euro", pair.getName());
	}

}
