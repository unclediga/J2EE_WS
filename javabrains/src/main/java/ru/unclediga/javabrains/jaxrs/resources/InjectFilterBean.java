package ru.unclediga.javabrains.jaxrs.resources;

import javax.ws.rs.CookieParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import java.util.List;

public class InjectFilterBean {
    @MatrixParam("mxParam1")
    private String matrixParam;
    @MatrixParam("mxParam2")
    private List<String> matrixParams;
    @HeaderParam("mySessionId")
    private String sid;
    @CookieParam("myName")
    private String cookie;

//    public InjectFilterBean(@MatrixParam("mxParam1") String matrixParam, @MatrixParam("mxParam2") List<String> matrixParams, @HeaderParam("mySessionId") String sid, @CookieParam("myName") String cookie) {
//        this.matrixParam = matrixParam;
//        this.matrixParams = matrixParams;
//        this.sid = sid;
//        this.cookie = cookie;
//    }

    public String getMatrixParam() {
        return matrixParam;
    }

    public List<String> getMatrixParams() {
        return matrixParams;
    }

    public String getSid() {
        return sid;
    }

    public String getCookie() {
        return cookie;
    }

    public void setMatrixParam(String matrixParam) {
        this.matrixParam = matrixParam;
    }

    public void setMatrixParams(List<String> matrixParams) {
        this.matrixParams = matrixParams;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
