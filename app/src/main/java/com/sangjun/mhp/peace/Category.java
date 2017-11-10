package com.sangjun.mhp.peace;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by Sangjun on 2017-10-20.
 */

@IgnoreExtraProperties
public class Category {

    public String name;
    public String address;
    public String phoneNumber;
    public int holiday;
    public OfficeHour officeHour;

    public Category() {
        this.name = null;
        this.address = null;
        this.phoneNumber = null;
        this.holiday = 0;
        this.officeHour = new OfficeHour();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getHoliday() {
        return holiday;
    }

    public void setHoliday(int holiday) {
        this.holiday = holiday;
    }

    public OfficeHour getOfficeHour() {
        return officeHour;
    }

    public void setOfficeHour(OfficeHour officeHour) {
        this.officeHour = officeHour;
    }

    @IgnoreExtraProperties
    public class OfficeHour {
        public String commonStart;
        public String commonEnd;
        public String specialStart;
        public String specialEnd;

        public OfficeHour() {
            this.commonStart = null;
            this.commonEnd = null;
            this.specialStart = null;
            this.specialEnd = null;
        }

        public String getCommonStart() {
            return commonStart;
        }

        public void setCommonStart(String commonStart) {
            this.commonStart = commonStart;
        }

        public String getCommonEnd() {
            return commonEnd;
        }

        public void setCommonEnd(String commonEnd) {
            this.commonEnd = commonEnd;
        }

        public String getSpecialStart() {
            return specialStart;
        }

        public void setSpecialStart(String specialStart) {
            this.specialStart = specialStart;
        }

        public String getSpecialEnd() {
            return specialEnd;
        }

        public void setSpecialEnd(String specialEnd) {
            this.specialEnd = specialEnd;
        }
    }

}
