package com.mateuszmidor.dataproviders;

/**
 * Class representing symbol-name pair, eg. EUR-Euro. Enforces
 * "symbol-always-uppercase" policy
 * 
 * @author Mateusz Midor
 * 
 */
public class SymbolNamePair {
    private String symbol;
    private String name;
    public static final SymbolNamePair EMPTY_ENTRY = new SymbolNamePair("[EMPTY]", "[empty]");

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
