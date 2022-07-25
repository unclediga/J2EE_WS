package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.core.Application;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MyData{
  private String value; 

  MyData(){
  }
  
  MyData(String value){
    setValue(value);
  }

  public String getValue(){
    return value;
  }

  public void setValue(String value){
    this.value = value;
  }
}



