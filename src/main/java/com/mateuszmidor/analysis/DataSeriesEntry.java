package com.mateuszmidor.analysis;

import java.util.Date;

/**
 * Represents single entry in DataSeries, in form of (float value, Date date)
 * 
 * @author Mateusz Midor
 * 
 */
public class DataSeriesEntry {
    public DataSeriesEntry() {}
    public DataSeriesEntry(double value, Date date) {
        this.value = value;
        this.date = date;
    }
    
    public double value;
    public Date date;
}
