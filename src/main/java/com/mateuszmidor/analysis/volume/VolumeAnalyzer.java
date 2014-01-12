package com.mateuszmidor.analysis.volume;

import com.mateuszmidor.analysis.AnalysisQuerry;
import com.mateuszmidor.analysis.AnalysisResult;
import com.mateuszmidor.analysis.Analyzer;
import com.mateuszmidor.analysis.DataSeries;
import com.mateuszmidor.analysis.DataSeriesEntry;
import com.mateuszmidor.data.Quotes;
import com.mateuszmidor.data.QuotesEntry;

/**
 * Returns volume component of stock quoting data.
 * 
 * @author mateusz
 * 
 */
public class VolumeAnalyzer implements Analyzer {

    @Override
    public AnalysisResult analyze(Quotes quotes, AnalysisQuerry querry) {
        DataSeries series = new DataSeries();
        for (QuotesEntry e : quotes) {
            series.add(new DataSeriesEntry(e.getVolume(), e.getDate()));
        }

        AnalysisResult result = new AnalysisResult();
        result.put("volume", series);
        return result;
    }

}
