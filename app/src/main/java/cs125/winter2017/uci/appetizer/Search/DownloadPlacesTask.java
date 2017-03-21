package cs125.winter2017.uci.appetizer.Search;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nokura on 3/20/2017.
 */

public class DownloadPlacesTask extends AsyncTask<String, String, String> {

    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... urls) {
        String charset = "UTF-8";
        String result = new String();
        try {
            for(int i = 0; i < urls.length; i++) {
                URL con = new URL(urls[i]);
                HttpURLConnection connect = (HttpURLConnection) con.openConnection();
                InputStreamReader in = new InputStreamReader(connect.getInputStream());
                //            BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                String line = null;
                StringBuilder sb = new StringBuilder();
                char[] buff = new char[1024];
                int read;
                while ((read = in.read(buff)) != -1) {
                    sb.append(buff, 0, read);
                }

                 result = sb.toString();

                in.close();
            }

        } catch (IOException e) {
            Log.e("DownloadPlacesTask", e.getMessage());
            e.printStackTrace();
        }
        return result;

    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }

}
