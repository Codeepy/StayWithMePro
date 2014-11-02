package com.example.android.factory;

import android.net.Uri;
import android.util.Log;
import com.example.android.enums.Codeepy;
import com.example.android.webservice.WebService;
import com.example.android.webservice.YoWebService;

/**
 * Created by cipherhat on 01/11/14.
 */
public class WebServiceURLFactory {

    private static WebServiceURLFactory instance = new WebServiceURLFactory();

    private WebServiceURLFactory() {
    }

    public static WebServiceURLFactory getInstance() {
        return instance;
    }

    public String buildUri(WebService ws) {
        Uri.Builder builder = Uri.parse(ws.getUrl()).buildUpon();

        if (ws instanceof YoWebService) {
            YoWebService yo = (YoWebService) ws;

            if (yo.getYo() != null) builder.path(yo.getYo());
        }

        Log.i(Codeepy.TAG.toString(), "Uri Build: " + builder.build().toString());
        return builder.build().toString();
    }
}
