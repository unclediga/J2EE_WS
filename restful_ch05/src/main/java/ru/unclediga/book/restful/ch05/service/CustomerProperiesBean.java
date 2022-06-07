package ru.unclediga.book.restful.ch05.service;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;

public class CustomerProperiesBean {

    @QueryParam("last-name")
    String lastName;
    @QueryParam("AGE")
    int age;
    @HeaderParam("Content-Language")
    String lang;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
