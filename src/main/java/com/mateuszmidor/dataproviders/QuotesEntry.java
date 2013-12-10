package com.mateuszmidor.dataproviders;

import java.util.Date;

/**
 * Single quotes data entry.
 * Holds Open, High, Low, Close, Volume and Date params.
 * @author Mateusz Midor
 *
 */
public class QuotesEntry {
	public double open;
	public double high;
	public double low;
	public double close;
	public double volume;
	public Date date;
	
	// can be used to mark the end of entries while fetching from a stream
	public static final QuotesEntry EMPTY_ENTRY = new QuotesEntry();
	public QuotesEntry() {
	}
	
	public QuotesEntry(double open, double high, double low, double close,
			double volume, Date date) {
		super();
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.date = date;
	}

}
