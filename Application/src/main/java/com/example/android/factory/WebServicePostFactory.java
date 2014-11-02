package com.example.android.factory;

import android.os.AsyncTask;
import android.util.Log;
import com.example.android.enums.Codeepy;
import com.example.android.webservice.WebService;
import com.example.android.webservice.YoWebService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cipherhat on 01/11/14.
 */
public class WebServicePostFactory extends AsyncTask<WebService, Void, String> {

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected String doInBackground(WebService... params) {
        String urlString = WebServiceURLFactory.getInstance().buildUri(params[0]).replace(" ", "%20");

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");


            String urlParameters = "api_token=" + ((YoWebService) params[0]).getApi_token() + "&username=" + ((YoWebService) params[0]).getUsername();
            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = urlConnection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception ex) {
            Log.e(Codeepy.TAG.toString(), ex.getMessage());
        }
        return Codeepy.ERROR.toString();
    }
}
