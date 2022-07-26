package ru.unclediga.javabrains.jaxrs;

import java.util.Calendar;

public class MyDay{
  private int day;
  private int month;
  private int year;

  MyDay(){
    Calendar c = Calendar.getInstance();
    day   = c.get(Calendar.DAY_OF_MONTH);
    month = c.get(Calendar.MONTH);
    year  = c.get(Calendar.YEAR);
  }

  public String toString(){
    return "MyDay:(" + day + "/" + month + "/" + year + ")";
  }

  public void incDay(){
    day++;
  }

  public void decDay(){
    day--;
  }
}

