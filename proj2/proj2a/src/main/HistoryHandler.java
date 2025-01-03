package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;


public class HistoryHandler extends NgordnetQueryHandler {

    private NGramMap ngm;

    public HistoryHandler(NGramMap ngm) {
        this.ngm = ngm;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (String word : words) {
            lts.add(ngm.weightHistory(word, q.startYear(), q.endYear()));
            labels.add(word);
        }
        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);
        return encodedImage;
    }
}
