package com.sangjun.mhp.peace;

import java.util.Comparator;

/**
 * Created by Sangjun on 2017-10-10.
 */

public class SearchData {
    public long time;
    public String data;

    public SearchData(long time, String data) {
        this.time = time;
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
