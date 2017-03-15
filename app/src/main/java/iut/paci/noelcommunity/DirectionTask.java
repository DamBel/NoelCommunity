package iut.paci.noelcommunity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Damien_Admin2 on 01/03/2017.
 */

public class DirectionTask extends AsyncTask<String, Void, String> {

    MapActivity activity;
    ProgressDialog pDialog;

    public DirectionTask(MapActivity activity){

        this.activity = activity;

    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pDialog = new ProgressDialog(this.activity);
        pDialog.setMessage("Attempting to get path information...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... args){

        String result = "";
        HttpURLConnection con = null;
        InputStream stream = null;

        try{

            URL url = new URL(args[0]);

            con = (HttpURLConnection) url.openConnection();

            con.setReadTimeout(10000);
            con.setConnectTimeout(10000);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();

            stream = con.getInputStream();

            if (con.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new IOException("HTTP error code:" + con.getResponseCode());

            BufferedReader buffer = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

            String line = "";

            while ((line = buffer.readLine()) != null)
                result += line + "\n";

            stream.close();

        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            con.disconnect();
            return result;
        }

    }

    @Override
    protected void onPostExecute(String result){

        Log.d("MapActivity", result);
        System.out.println("\n\n #########\n RESULTAT : " + result + "\n\n #########");

        this.activity.drawPath(this.activity.getPathFromJson(result));

        pDialog.dismiss();

        //Traitement du résultat

    }





}
