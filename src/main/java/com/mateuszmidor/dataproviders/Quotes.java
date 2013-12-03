package com.mateuszmidor.dataproviders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Represents series of quotes data identified by symbol name and full name.
 * That's what you usually get from quotation providers over the Web.
 * 
 * @author Mateusz Midor
 * 
 */
public class Quotes implements Iterable<QuotesEntry> {

	private final List<QuotesEntry> data = new ArrayList<QuotesEntry>();
	private final String symbolName;
	private final String fullName;

	public Quotes(String symbolName, String fullName) {
		this.symbolName = symbolName;
		this.fullName = fullName;
	}

	public String getSymbolName() {
		return symbolName;
	}

	public String getFullName() {
		return fullName;
	}

	public Boolean isEmpty() {
		return data.isEmpty();
	}

	public void insertEntry(QuotesEntry entry) {
		data.add(entry);
	}

	public void sortAscending() {
		Comparator<QuotesEntry> cmp = new Comparator<QuotesEntry>() {
			public int compare(QuotesEntry e1, QuotesEntry e2) {
				return e1.date.compareTo(e2.date);
			}
		};

		Collections.sort(data, cmp);
	}

	@Override
	public Iterator<QuotesEntry> iterator() {
		return data.iterator();
	}

}
