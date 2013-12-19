package com.mateuszmidor.data;

import java.util.LinkedList;

/**
 * List of symbols to pass around, eg. CDR, KGHM, PZU 
 * @author Mateusz Midor
 *
 */
public class Symbols extends LinkedList<String> {
	private static final long serialVersionUID = 1L;
    public static final Symbols EMPTY_SYMBOLS = new Symbols();
}
