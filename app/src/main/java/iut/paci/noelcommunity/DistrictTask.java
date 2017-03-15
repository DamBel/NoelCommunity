package iut.paci.noelcommunity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.app.Activity;
import org.mapsforge.core.model.LatLong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Damien_Admin2 on 27/02/2017.
 */

public class DistrictTask extends AsyncTask<String, Void, String> {


    private final MapActivity activity;
    private final String url;
    private ProgressDialog pDialog;

    public DistrictTask(MapActivity activity, String url){
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Attempting to get district information\n" + this.url);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

    }

    @Override
    protected String doInBackground(String... args) {

        InputStream stream = null;
        HttpURLConnection con = null;
        String result = "", line = "";

        try {
            URL url = new URL(args[0]);
            con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(10000); con.setConnectTimeout(10000);
            con.setRequestMethod("GET"); con.setDoInput(true);
            con.connect();
            if (con.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new IOException("HTTP error code: " + con.getResponseCode());
            stream = con.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            while ((line = buffer.readLine()) != null) {
                result += line + "\n";
            }
            pDialog.setMessage(result);
        } catch (IOException e) {
            pDialog.setMessage(e.toString());
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch(IOException ex) {
            }
            con.disconnect();
            return result;
        }
    }

    @Override
    protected void onPostExecute(String result) {

        pDialog.dismiss();
        Log.d("MapActivity", result);
        // Traitement du résultat

        try {
            District d = District.fromJson(this.get());
            for(Store s : d.getStores()) {
                this.activity.drawMaker(R.drawable.sapin, new LatLong(s.getLatitude(), s.getLongitude()));
            }
            /*
            for(Deposite de : d.getDeposites()) {
                this.activity.drawMaker(de.getIdRessource(), new LatLong(de.getLatitude(), de.getLongitude()));
            }
            */

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }



    }
}
