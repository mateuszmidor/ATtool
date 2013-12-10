package com.mateuszmidor.dataproviders;


/**
 * Base class for various sources of quotes data providers
 * 
 * @author mateusz
 * 
 */
public abstract class DataProvider {
	private SymbolToNameMap symbolToNameMap = new SymbolToNameMap();

	// pattern: algorithm
	// below three functions to be implemented in derived classes
	abstract public String getGroupName();
	
	abstract protected Quotes fetchQuotesForSymbol(String symbol)
			throws DataProviderException;
	
	abstract protected SymbolToNameMap fetchSymbolToNameMap()
			throws DataProviderException;

	
	private SymbolToNameMap getSymbolToNameMap()
			throws DataProviderException {
		
		// lazy initialization
		if (symbolToNameMap.isEmpty()) {
			symbolToNameMap = fetchSymbolToNameMap();
		}

		return symbolToNameMap;
	}

	public boolean isSymbolAvailable(String symbol)
			throws DataProviderException {
		
		String upcaseSymbol = symbol.toUpperCase();
		return getSymbolToNameMap().keySet().contains(upcaseSymbol);
	}

	public Quotes getQuotesForSymbol(String symbol)
			throws DataProviderException {
		
		Quotes data = fetchQuotesForSymbol(symbol);
		return data;
	}

	public String getFullNameForSymbol(String symbol)
			throws DataProviderException {
		
		return getSymbolToNameMap().get(symbol);
	}

	public Symbols getAvailableSymbols() throws DataProviderException {
		
		Symbols symbols = new Symbols();
		symbols.addAll(getSymbolToNameMap().keySet());
		return symbols;
	}
}
