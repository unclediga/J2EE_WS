package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.core.Application;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MyData{
  private String value;
  @XmlElement(name = "uid")
  private String deviceId = "#defaultNum";

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

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  @Override
  public String toString() {
    return "MyData{" +
            "value='" + value + '\'' +
            ", deviceId='" + deviceId + '\'' +
            '}';
  }
}



