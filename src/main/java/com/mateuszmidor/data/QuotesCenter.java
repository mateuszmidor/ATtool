package com.mateuszmidor.data;

/**
 * Interface for fetching quotes from any previously added DataProvider
 * @author Mateusz Midor
 *
 */
public interface QuotesCenter {
    void addDataProvider(DataProvider provider) throws DataProviderException;
    Groups getGroups();
    Symbols getSymbols(String groupName);
    
    /**
     * 
     *  Address the Quotes by symbol name and group name - 
     *  in case there is more groups providing the same symbol
     */
    Quotes getQuotes(String symbolName, String groupName);
}
