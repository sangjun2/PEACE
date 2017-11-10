package com.sangjun.mhp.peace;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Sangjun on 2017-09-25.
 */

@IgnoreExtraProperties
public class Place {
    public double longitude;
    public double latitude;
    public String name;
    public Time time;
    public Price price;
    public String url;
    public int searchCount;
    public int starCount;
    public int pictureLength;

    public Place() {
        this.longitude = -1;
        this.latitude = -1;
        this.name = null;
        this.time = new Time();
        this.price = new Price();
        this.url = null;
        this.searchCount = -1;
        this.starCount = -1;
        this.pictureLength = -1;
    }

    public int getPictureLength() {
        return pictureLength;
    }

    public void setPictureLength(int pictureLength) {
        this.pictureLength = pictureLength;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    @IgnoreExtraProperties
    public static class Price {
        public int student;
        public int adult;

        public Price() {
            this.student = -1;
            this.adult = -1;
        }

        public Price(int student, int adult) {
            this.student = student;
            this.adult = adult;
        }

        public int getStudent() {
            return student;
        }

        public void setStudent(int student) {
            this.student = student;
        }

        public int getAdult() {
            return adult;
        }

        public void setAdult(int adult) {
            this.adult = adult;
        }
    }

    @IgnoreExtraProperties
    public static class Time {
        public int min;
        public int max;

        public Time() {
            this.min = -1;
            this.max = -1;
        }

        public Time(int min, int max) {
            this.min = min;
            this.max = max;
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
}
