package com.mateuszmidor.data;

import java.util.Date;

/**
 * Single quotes data entry.
 * Holds Open, High, Low, Close, Volume and Date params.
 * @author Mateusz Midor
 *
 */
public class QuotesEntry {
	private double open;
	private double high;
	private double low;
	private double close;
	private double volume;
	private Date date;
	
	// can be used to mark the end of entries while fetching from a stream
	public static final QuotesEntry EMPTY_ENTRY = new QuotesEntry();
	public QuotesEntry() {
	}
	
	public QuotesEntry(double open, double high, double low, double close,
			double volume, Date date) {
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.date = date;
	}

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
