package com.mateuszmidor.dataproviders;

/**
 * Class representing symbol-name pair, eg. EUR-EURO. Enforces "symbol is always
 * uppercase" policy
 * 
 * @author Mateusz Midor
 * 
 */
public class SymbolNamePair {
	private String symbol;
	private String name;
	static final public SymbolNamePair EMPTY_ENTRY = new SymbolNamePair(
			"[EMPTY]", "[empty]");

	public SymbolNamePair(String symbol, String name) {
		this.symbol = symbol.toUpperCase();
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

}
