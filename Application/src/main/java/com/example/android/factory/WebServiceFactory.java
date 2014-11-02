package com.example.android.factory;

import android.os.AsyncTask;
import android.util.Log;
import com.example.android.enums.Codeepy;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WebServiceFactory extends AsyncTask<String, Void, String> {

    protected String doInBackground(String... urls) {
        String urlString = urls[0].replace(" ", "%20");

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Scanner s = new Scanner(in).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (Exception ex) {
            Log.e(Codeepy.TAG.toString(), ex.getMessage());
        }
        return Codeepy.ERROR.toString();
    }
}