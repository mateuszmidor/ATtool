package com.mateuszmidor.analysis.price;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mateuszmidor.analysis.AnalysisResult;
import com.mateuszmidor.analysis.Analyzer;
import com.mateuszmidor.analysis.DataSeries;
import com.mateuszmidor.data.Quotes;
import com.mateuszmidor.data.QuotesEntry;

public class CandlePriceAnalyzerTest {

    private static final double DELTA = 0.001;
    private Quotes quotes;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setup() {
        quotes = new Quotes("tst", "test");
        
        // open high low close volume date
        quotes.insertEntry(new QuotesEntry(10, 20, 5, 15, 0, null));
        quotes.insertEntry(new QuotesEntry(11, 21, 6, 16, 0, null));
        quotes.insertEntry(new QuotesEntry(12, 22, 7, 17, 0, null));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        Analyzer testObject = new CandlePriceAnalyzer();
        AnalysisResult result = testObject.analyze(quotes, null);

        // check stock open values
        DataSeries open = result.get("open");
        Assert.assertNotNull(open);
        Assert.assertEquals(10, open.get(0).getValue(), DELTA);
        Assert.assertEquals(11, open.get(1).getValue(), DELTA);
        Assert.assertEquals(12, open.get(2).getValue(), DELTA);

        // check stock high values
        DataSeries high = result.get("high");
        Assert.assertNotNull(high);
        Assert.assertEquals(20, high.get(0).getValue(), DELTA);
        Assert.assertEquals(21, high.get(1).getValue(), DELTA);
        Assert.assertEquals(22, high.get(2).getValue(), DELTA);

        // check stock low values
        DataSeries low = result.get("low");
        Assert.assertNotNull(low);
        Assert.assertEquals(5, low.get(0).getValue(), DELTA);
        Assert.assertEquals(6, low.get(1).getValue(), DELTA);
        Assert.assertEquals(7, low.get(2).getValue(), DELTA);

        // check stock close values
        DataSeries close = result.get("close");
        Assert.assertNotNull(close);
        Assert.assertEquals(15, close.get(0).getValue(), DELTA);
        Assert.assertEquals(16, close.get(1).getValue(), DELTA);
        Assert.assertEquals(17, close.get(2).getValue(), DELTA);
    }

}
