package com.sangjun.mhp.peace;

/**
 * Created by Sangjun on 2017-11-03.
 */

public class ReserveUser {
    public String name;
    public String phone;
    public String email;
    public String content;
    public int number;
    public String type;

    public ReserveUser() {
        this.name = null;
        this.phone = null;
        this.email = null;
        this.content = null;
        this.number = 0;
        this.type = null;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
