package com.mateuszmidor.analysis;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DataSeriesEntryTest {

    private static final double DELTA = 0.001;

    @Test
    public void test() {
        final Date date = new Date();
        final double value = 100.0;
        DataSeriesEntry testObject = new DataSeriesEntry(value, date);
        
        Assert.assertEquals(value, testObject.getValue(), DELTA);
        Assert.assertEquals(date, testObject.getDate());
    }

}
