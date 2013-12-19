package com.mateuszmidor.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for various sources of quotes data providers
 * 
 * @author mateusz
 * 
 */
public abstract class DataProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataProvider.class);
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

    public Quotes getQuotesForSymbol(String symbol) {

        try {
            return fetchQuotesForSymbol(symbol);
        } catch (DataProviderException e) {
            LOGGER.error(String.format("Quotes fetching failed for %s(%s)", symbol, getGroupName()), e);
            return Quotes.EMPTY_QUOTES;
        }
    }

    public String getFullNameForSymbol(String symbol) throws DataProviderException {

        return getSymbolToNameMap().get(symbol);
    }

    public Symbols getAvailableSymbols() {

        Symbols symbols = new Symbols();
        try {
            symbols.addAll(getSymbolToNameMap().keySet());
            return symbols;
        } catch (DataProviderException e) {
            LOGGER.error(String.format("Symbols fetching failed for group %s", getGroupName()), e);
            return Symbols.EMPTY_SYMBOLS;
        }

    }
}
