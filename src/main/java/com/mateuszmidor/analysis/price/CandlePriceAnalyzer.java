package com.mateuszmidor.analysis.price;

import com.mateuszmidor.analysis.AnalysisQuerry;
import com.mateuszmidor.analysis.AnalysisResult;
import com.mateuszmidor.analysis.Analyzer;
import com.mateuszmidor.analysis.DataSeries;
import com.mateuszmidor.analysis.DataSeriesEntry;
import com.mateuszmidor.data.Quotes;
import com.mateuszmidor.data.QuotesEntry;

/**
 * Returns open, high, low, close components of stock quoting data.
 * 
 * @author mateusz
 * 
 */
public class CandlePriceAnalyzer implements Analyzer {

    @Override
    public AnalysisResult analyze(Quotes quotes, AnalysisQuerry querry) {
        DataSeries open = new DataSeries();
        DataSeries high = new DataSeries();
        DataSeries low = new DataSeries();
        DataSeries close = new DataSeries();
        
        for (QuotesEntry e : quotes) {
            open.add(new DataSeriesEntry(e.getOpen(), e.getDate()));
            high.add(new DataSeriesEntry(e.getHigh(), e.getDate()));
            low.add(new DataSeriesEntry(e.getLow(), e.getDate()));
            close.add(new DataSeriesEntry(e.getClose(), e.getDate()));
        }

        AnalysisResult result = new AnalysisResult();
        result.put("open", open);
        result.put("high", high);
        result.put("low", low);
        result.put("close", close);
        
        return result;
    }

}
