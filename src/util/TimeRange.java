package util;

/**
 * Created by dcmeade on 4/19/2017.
 */
public enum TimeRange
{
    // Base Time is 1 day
    DAY(1),
    WEEK(DAY.dayCount      * 7),
    WEEK_2(WEEK.dayCount   * 2),
    MONTH(DAY.dayCount     * 30), // Just an average of the month days
    MONTH_2(MONTH.dayCount * 2),
    MONTH_3(MONTH.dayCount * 3),
    MONTH_6(MONTH.dayCount * 4),
    YEAR(DAY.dayCount      * 365),
    All(DAY.dayCount       * Integer.MAX_VALUE);

    private int dayCount;

    TimeRange(int dayCount)
    {
        this.dayCount = dayCount;
    }

    public int getDayCount()
    {
        return dayCount;
    }
}
