package ru.unclediga.javabrains.jaxrs.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
    private String message;
    private int code;
    private String link;

    public ErrorMessage() {
    }

    public ErrorMessage(String message, int code, String link) {
        this.message = message;
        this.code = code;
        this.link = link;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
