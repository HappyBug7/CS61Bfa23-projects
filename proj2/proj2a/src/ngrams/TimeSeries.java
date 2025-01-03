package ngrams;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (int i = startYear; i <= endYear; i++) {
            put(i, ts.get(i));
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        List<Integer> years = new ArrayList<>();
        for (Integer year : keySet()) {
            years.add(year);
        }
        return years;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> data = new ArrayList<>();
        for (Integer year : years()) {
            data.add(get(year));
        }
        return data;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries returnSeries = new TimeSeries();
        for (Integer year : years()) {
            returnSeries.put(year, get(year));
        }
        for (Integer year : ts.years()) {
            if (returnSeries.containsKey(year)) {
                returnSeries.replace(year, returnSeries.get(year) + ts.get(year));
            } else {
                returnSeries.put(year, ts.get(year));
            }
        }
        return returnSeries;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries returnSeries = new TimeSeries();
        for (Integer year : years()) {
            if (ts.containsKey(year)) {
                returnSeries.put(year, get(year) / ts.get(year));
            } else {
                throw new IllegalArgumentException();
            }
        }
        return returnSeries;
    }

    public TimeSeries copy() {
        TimeSeries returnSeries = new TimeSeries();
        for (Integer year : years()) {
            returnSeries.put(year, get(year));
        }
        return returnSeries;
    }

}
