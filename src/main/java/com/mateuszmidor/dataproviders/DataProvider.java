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
	
	abstract protected Quotes getDataForSymbol(String symbol)
			throws DataProviderException;
	
	abstract protected SymbolToNameMap loadSymbolsToNamesMap()
			throws DataProviderException;

	
	private SymbolToNameMap getSymbolToNameMap()
			throws DataProviderException {
		
		// lazy initialization
		if (symbolToNameMap.isEmpty()) {
			symbolToNameMap = loadSymbolsToNamesMap();
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
		
		Quotes data = getDataForSymbol(symbol);
		data.sortAscending();
		return data;
	}

	public String getFullNameForSymbol(String symbol)
			throws DataProviderException {
		
		return getSymbolToNameMap().get(symbol);
	}

	public Symbols getSymbolList() throws DataProviderException {
		
		Symbols symbols = new Symbols();
		symbols.addAll(getSymbolToNameMap().keySet());
		return symbols;
	}
}
