package com.mateuszmidor.data;

import java.util.HashMap;
import java.util.Map;

/**
 * DefaultQuotesCenter provides quotes data thanks to it's held data providers.
 * No data caching is provided.
 * 
 * @author Mateusz Midor
 * 
 */
public class DefaultQuotesCenter implements QuotesCenter {
    private Map<String, DataProvider> providers = new HashMap<>();

    @Override
    public void addDataProvider(DataProvider provider) throws DataProviderException {
        String groupName = provider.getGroupName();
        throwOnDuplicatedProviderForGroup(groupName);
        providers.put(groupName, provider);
    }

    private void throwOnDuplicatedProviderForGroup(String groupName) throws DataProviderException {
        if (null != getProviderForGroupName(groupName)) {
            throw new DataProviderException(String.format("Provider for group %s already on the list", groupName));
        }
    }

    @Override
    public Groups getGroups() {
        Groups groups = new Groups();
        groups.addAll(providers.keySet());
        return groups;
    }

    @Override
    public Symbols getSymbols(String groupName) {
        DataProvider provider = getProviderForGroupName(groupName);
        return provider.getAvailableSymbols();
    }

    @Override
    public Quotes getQuotes(String symbolName, String groupName) {
        DataProvider provider = getProviderForGroupName(groupName);
        return provider.getQuotesForSymbol(symbolName);
    }

    private DataProvider getProviderForGroupName(String groupName) {
        return providers.get(groupName);
    }

}
