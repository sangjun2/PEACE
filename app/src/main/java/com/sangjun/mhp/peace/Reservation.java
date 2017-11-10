package com.sangjun.mhp.peace;

import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sangjun on 2017-11-08.
 */

@IgnoreExtraProperties
public class Reservation {
    public String id;
    public String place;
    public String date;
    public String dateFormat;
    public String time;
    public String number;
    public String name;
    public String phone;
    public String email;
    public String content;

    public Reservation() {

    }

    public Reservation(String id, String place, String date, String dateFormat, String time, String number, String name, String phone, String email, String content) {
        this.id = id;
        this.place = place;
        this.date = date;
        this.dateFormat = dateFormat;
        this.time = time;
        this.number = number;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.content = content;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("place", place);
        map.put("date", dateFormat);
        map.put("time", time);
        map.put("number", number);
        map.put("name", name);
        map.put("phone", phone);
        map.put("email", email);
        map.put("content", content);

        return map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
