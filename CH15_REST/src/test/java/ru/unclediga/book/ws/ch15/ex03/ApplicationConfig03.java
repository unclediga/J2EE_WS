package ru.unclediga.book.ws.ch15.ex03;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rs")
public class ApplicationConfig03 extends Application {
  private Set<Class<?>> classes;

  ApplicationConfig03(){
    Set<Class<?>> s = new HashSet<>();
    s.add(BookRestService.class);
    s.add(RootRestService.class);
    classes =  Collections.unmodifiableSet(s);
  }

  public Set<Class<?>> getClasses(){
    return classes;
  } 

}