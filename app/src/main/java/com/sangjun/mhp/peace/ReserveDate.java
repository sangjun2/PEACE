package com.sangjun.mhp.peace;

import com.prolificinteractive.materialcalendarview.CalendarDay;

/**
 * Created by Sangjun on 2017-11-02.
 */

public class ReserveDate {
    public CalendarDay date;
    public int min;
    public int max;

    public ReserveDate(CalendarDay date, int min, int max) {
        this.date = date;
        this.min = min;
        this.max = max;
    }

    public CalendarDay getDate() {
        return date;
    }

    public void setDate(CalendarDay date) {
        this.date = date;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
