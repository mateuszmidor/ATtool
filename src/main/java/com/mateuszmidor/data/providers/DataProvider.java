package com.mateuszmidor.data.providers;

import com.mateuszmidor.data.Quotes;
import com.mateuszmidor.data.SymbolNameMap;
import com.mateuszmidor.data.Symbols;

/**
 * Base class for various sources of quotes data providers
 * 
 * @author mateusz
 * 
 */
public abstract class DataProvider {
    private SymbolNameMap symbolToNameMap = new SymbolNameMap();

    // pattern: algorithm
    // below three functions to be implemented in derived classes
    public abstract String getGroupName();

    protected abstract Quotes fetchQuotesForSymbol(String symbol) throws DataProviderException;

    protected abstract SymbolNameMap fetchSymbolToNameMap() throws DataProviderException;

    private SymbolNameMap getSymbolToNameMap() throws DataProviderException {

        // lazy initialization
        if (symbolToNameMap.isEmpty()) {
            symbolToNameMap = fetchSymbolToNameMap();
        }

        return symbolToNameMap;
    }

    public boolean isSymbolAvailable(String symbol) throws DataProviderException {

        String upcaseSymbol = symbol.toUpperCase();
        return getSymbolToNameMap().keySet().contains(upcaseSymbol);
    }

    public Quotes getQuotesForSymbol(String symbol) throws DataProviderException {

        return fetchQuotesForSymbol(symbol);
    }

    public String getFullNameForSymbol(String symbol) throws DataProviderException {

        return getSymbolToNameMap().get(symbol);
    }

    public Symbols getAvailableSymbols() throws DataProviderException {

        Symbols symbols = new Symbols();
        symbols.addAll(getSymbolToNameMap().keySet());
        return symbols;
    }
}
