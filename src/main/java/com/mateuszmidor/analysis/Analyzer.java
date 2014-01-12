package com.mateuszmidor.analysis;

import com.mateuszmidor.data.Quotes;

public interface Analyzer {
    AnalysisResult analyze(Quotes quotes, AnalysisQuerry querry);
}
