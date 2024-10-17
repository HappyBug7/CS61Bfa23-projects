package ngrams;

import edu.princeton.cs.algs4.In;

import java.sql.Time;
import java.util.Collection;
import java.util.TreeMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private TreeMap<String, TimeSeries> wordMap;
    private TimeSeries totalCounts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In words = new In(wordsFilename);
        In counts = new In(countsFilename);
        wordMap = new TreeMap<>();
        totalCounts = new TimeSeries();
        while (words.hasNextLine()) {
            String nextLine = words.readLine();
            String[] split = nextLine.split("\t");
            String word = split[0];
            String year = split[1];
            String count = split[2];
            if (wordMap.containsKey(word)) {
                TimeSeries ts = wordMap.get(word);
                ts.put(Integer.parseInt(year), Double.parseDouble(count));
            } else {
                TimeSeries ts = new TimeSeries();
                ts.put(Integer.parseInt(year), Double.parseDouble(count));
                wordMap.put(word, ts);
            }
        }
        while (counts.hasNextLine()) {
            String nextLine = counts.readLine();
            String[] split = nextLine.split(",");
            String year = split[0];
            String count = split[1];
            totalCounts.put(Integer.parseInt(year), Double.parseDouble(count));
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (wordMap.containsKey(word)) {
            return new TimeSeries(wordMap.get(word), startYear, endYear);
        } else  {
            return new TimeSeries();
        }
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (wordMap.containsKey(word)) {
            return wordMap.get(word).copy();
        } else  {
            return new TimeSeries();
        }
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return totalCounts.copy();
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (wordMap.containsKey(word)) {
            TimeSeries count = new TimeSeries(totalCounts, startYear, endYear);
            TimeSeries words = new TimeSeries(wordMap.get(word), startYear, endYear);
            return words.dividedBy(count);
        } else  {
            return new TimeSeries();
        }
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (wordMap.containsKey(word)) {
            return wordMap.get(word).dividedBy(totalCounts).copy();
        } else  {
            return new TimeSeries();
        }
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries summedWeights = new TimeSeries();
        for (String word : words) {
            summedWeights =  summedWeights.plus(weightHistory(word, startYear, endYear));
        }
        return summedWeights;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries summedWeights = new TimeSeries();
        for (String word : words) {
            summedWeights = summedWeights.plus(weightHistory(word));
        }
        return summedWeights;
    }

}
