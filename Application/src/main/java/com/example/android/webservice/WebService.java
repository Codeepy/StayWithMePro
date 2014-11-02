package com.example.android.webservice;

public abstract class WebService {
    public static final String TAG = "Web Service";

    public static final int FORMAT_JSON = 0;
    protected static final String JSON = "json";

    public static final int FORMAT_XML = 1;
    protected static final String XML = "xml";

    private String format;

    protected String url;

    protected WebService() {
    }

    protected WebService(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(int format) {
        if (format == FORMAT_JSON)
            this.format = JSON;
        else if (format == FORMAT_XML)
            this.format = XML;
    }

    public void clearFormat() {
        this.format = null;
    }

    public void clearEverything(){
        clearFormat();
    }
}