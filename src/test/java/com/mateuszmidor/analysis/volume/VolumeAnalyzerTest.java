package com.mateuszmidor.analysis.volume;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.mateuszmidor.analysis.AnalysisResult;
import com.mateuszmidor.analysis.Analyzer;
import com.mateuszmidor.analysis.DataSeries;
import com.mateuszmidor.data.Quotes;
import com.mateuszmidor.data.QuotesEntry;

public class VolumeAnalyzerTest {

    private static final double DELTA = 0.001;
    private Quotes quotes;

    @Before
    public void setup() {
        quotes = new Quotes("tst", "test");
        quotes.insertEntry(new QuotesEntry(0, 0, 0, 0, 10, null));
        quotes.insertEntry(new QuotesEntry(0, 0, 0, 0, 20, null));
        quotes.insertEntry(new QuotesEntry(0, 0, 0, 0, 30, null));
        quotes.insertEntry(new QuotesEntry(0, 0, 0, 0, 40, null));
        quotes.insertEntry(new QuotesEntry(0, 0, 0, 0, 50, null));
    }

    @Test
    public void test() {
        Analyzer testObject = new VolumeAnalyzer();
        AnalysisResult result = testObject.analyze(quotes, null);
        DataSeries volume = result.get("volume");

        Assert.assertNotNull(volume);
        Assert.assertEquals(10, volume.get(0).getValue(), DELTA);
        Assert.assertEquals(20, volume.get(1).getValue(), DELTA);
        Assert.assertEquals(30, volume.get(2).getValue(), DELTA);
        Assert.assertEquals(40, volume.get(3).getValue(), DELTA);
        Assert.assertEquals(50, volume.get(4).getValue(), DELTA);
    }

}
