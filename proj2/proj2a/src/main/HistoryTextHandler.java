package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {

    private NGramMap nmp;

    public HistoryTextHandler(NGramMap nmp) {
        this.nmp = nmp;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";

        for (String word : words) {
            TimeSeries ts = nmp.weightHistory(word, startYear, endYear);
            response += word + ": " + ts.toString() + "\n";
        }
        return response;
    }
}
