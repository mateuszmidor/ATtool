package com.mateuszmidor.analysis;

import java.util.Date;

/**
 * Represents single entry in DataSeries, in form of (float value, Date date)
 * 
 * @author Mateusz Midor
 * 
 */
public class DataSeriesEntry {
    private double value;
    private Date date;

    public DataSeriesEntry() {
    }

    public DataSeriesEntry(double value, Date date) {
        this.value = value;
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
