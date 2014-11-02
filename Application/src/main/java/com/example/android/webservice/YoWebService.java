package com.example.android.webservice;

/**
 * Created by cipherhat on 01/11/14.
 */
public class YoWebService extends WebService {

    public static final String YO = "yo/";
    public static final String YO_ALL = "yoall/";

    private String yo;
    private String api_token;
    private String username;

    public YoWebService() {
        super("http://api.justyo.co/");
        clearEverything();
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public void clearApi_token() {
        this.api_token = null;
    }

    public String getYo() {
        return yo;
    }

    public void setYo(String yo) {
        this.yo = yo;
    }

    public void clearYo() {
        this.yo = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void clearUsername() {
        this.username = null;
    }

    @Override
    public void clearEverything() {
        super.clearEverything();
        clearApi_token();
        clearYo();
        clearUsername();
    }
}