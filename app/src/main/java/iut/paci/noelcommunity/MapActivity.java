package iut.paci.noelcommunity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.graphics.Style;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.datastore.MapDataStore;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.layer.overlay.Polyline;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    MapView mapView;
    TileRendererLayer tileRendererLayer;
    TileCache tileCache;
    District district;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_map);

        /* Géolocalisation */

        this.géolocalisation();

        /*******************/

        AndroidGraphicFactory.createInstance(this.getApplication());

        mapView = new MapView(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        setContentView(mapView);

        mapView.setClickable(true);
        mapView.getMapScaleBar().setVisible(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setZoomLevelMin((byte) 10);
        mapView.setZoomLevelMax((byte) 20);

        tileCache = AndroidUtil.createTileCache(this, "mapcache",
                mapView.getModel().displayModel.getTileSize(), 1f,
                mapView.getModel().frameBufferModel.getOverdrawFactor()
        );

        this.afficherChemin();

        this.getTrees(1);

    }

    public void getTrees(int id) {

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("http")
                .authority("paci.misc-lab.org")
                .appendPath("getDistrict.php")
                .appendQueryParameter("id", id + "");

        String urlString = builder.build().toString();

        DistrictTask districtTask = new DistrictTask(MapActivity.this);

        String result = districtTask.execute(urlString).toString();

        district = District.fromJson(result);

    }

    @Override
    public void onStart() {

        super.onStart();

        File file = new File(Environment.getExternalStorageDirectory(), "/maps/paris.map");

        MapDataStore mapDataStore = new MapFile(file);

        tileRendererLayer = new TileRendererLayer(tileCache, mapDataStore,
                mapView.getModel().mapViewPosition, AndroidGraphicFactory.INSTANCE
        ) {
            @Override
            public boolean onLongPress(LatLong tapLatLong, Point layerXY, Point tapXY) {

                super.onLongPress(tapLatLong, layerXY, tapXY);

                drawMaker(R.drawable.ic_place_black_24dp, tapLatLong);

                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);

                return true;
            }

        };

        tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.OSMARENDER);

        mapView.getLayerManager().getLayers().add(tileRendererLayer);
        mapView.setCenter(new LatLong(48.841751, 2.2684444));
        mapView.setZoomLevel((byte) 19);

        /*for(int i=1; i<21; ++i) {

            this.getTrees(i);

            for (Store s : district.stores) {

                LatLong l = new LatLong(s.latitude, s.longitude);
                drawMaker(R.drawable.sapin, l);

            }

            for (Deposite d : district.deposites) {

                LatLong l = new LatLong(d.latitude, d.longitude);
                drawMaker(R.drawable.sapin, l);

            }
        }*/
    }

    @Override
    public void onDestroy() {
        mapView.destroyAll();
        AndroidGraphicFactory.clearResourceMemoryCache();
        super.onDestroy();
    }

    public void drawMaker(int resourceId, LatLong geoPoint) {

        Drawable drawable = getResources().getDrawable(resourceId);
        Bitmap bitmap = AndroidGraphicFactory.convertToBitmap(drawable);
        bitmap.scaleTo(130, 130);
        Marker marker = new Marker(geoPoint, bitmap, 0, -bitmap.getHeight() / 2) {

            @Override
            public boolean onTap(LatLong geoPoint, Point viewpos, Point tapPoint) {

                if (contains(viewpos, tapPoint)) {
                    Toast.makeText(MapActivity.this, "clicked marker", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        };

        mapView.getLayerManager().getLayers().add(marker);

    }

    public void drawPath(List<LatLong> path){

        Paint paint = AndroidGraphicFactory.INSTANCE.createPaint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(15);
        paint.setStyle(Style.STROKE);

        Polyline polyline = new Polyline(paint, AndroidGraphicFactory.INSTANCE);

        List<LatLong> coordinateList = polyline.getLatLongs();

        for (LatLong geoPoint : path){
            coordinateList.add(geoPoint);
        }

        mapView.getLayerManager().getLayers().add(polyline);
    }

    public void géolocalisation() {

        /** Autorisation **/

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        /******************/

        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        /*Criteria critere = new Criteria();
        critere.setAccuracy(Criteria.ACCURACY_FINE);
        critere.setAltitudeRequired(true);
        critere.setBearingRequired(true);
        critere.setCostAllowed(false);
        critere.setPowerRequirement(Criteria.POWER_HIGH);
        critere.setSpeedRequired(true);*/

        final String provider = LocationManager.NETWORK_PROVIDER;

        Location location = lm.getLastKnownLocation(provider);
        Log.i("MapActivity", "Le provider " + provider + " a été sélectionné!");

        /*if (location != null) {
            Log.d("MapActivity", "Position trouvée!");
            location.getLongitude();
            location.getLatitude();
            location.getAltitude();
        } else {
            Log.d("MapActivity", "aucune position connue");
        }*/

        LocationListener locationListener = new LocationListener() {

            /**
             * Quand la localisation de l'utilisateur est mise à jour
             *
             * @param location
             */
            @Override
            public void onLocationChanged(Location location) {



                drawMaker(R.drawable.jesuisici,
                        new LatLong(location.getLatitude(), location.getLongitude()));

            }

            /**
             * Quand le status d'une source change
             *
             * @param provider
             * @param status
             * @param extras
             */
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            /**
             * Quand une source de localisation est activée (GPS, 3G, Wifi, ...)
             *
             * @param provider
             */
            @Override
            public void onProviderEnabled(String provider) {

            }

            /**
             * Quand une source de localisation est désactivée (GPS, 3G, ...)
             *
             * @param provider
             */
            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        int minTime = 0;
        float minDistance = 0;

        lm.requestLocationUpdates(provider, minTime, minDistance, locationListener);

    }

    public List<LatLong> getPathFromJson(String json){

        try{
            List<LatLong> path = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray maneuversObj = jsonObject.getJSONObject("route")
                    .getJSONArray("legs").getJSONObject(0)
                    .getJSONArray("maneuvers");

            for(int i = 0; i < maneuversObj.length(); ++i){

                JSONObject obj = maneuversObj.getJSONObject(i).getJSONObject("startPoint");
                LatLong point = new LatLong(obj.getDouble("lat"), obj.getDouble("lng"));
                path.add(point);
            }

            return path;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public void afficherChemin(){

        String requête = "http://www.mapquestapi.com/directions/v2/route?key=deamSBfbxULjOkFvP9dW1QiAKewVYxVg&json={locations:[{latLng:{lat:48.8418565,lng:2.2683737}},{latLng:{lat:48.846275,lng:2.354964}}]}";

        DirectionTask directionTask = new DirectionTask(MapActivity.this);

        directionTask.execute(requête);
    }
}
