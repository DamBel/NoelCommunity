package iut.paci.noelcommunity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import org.mapsforge.core.model.LatLong;

/**
 * Created by Damien_Admin2 on 15/03/2017.
 */

public class MarkerDialog extends Dialog implements View.OnClickListener {

    MapActivity activity;
    ImageButton bouton;
    LatLong position_courante;
    LatLong destination;

    public MarkerDialog(MapActivity activity, LatLong position, LatLong destination){
        super(activity);
        this.activity = activity;
        this.position_courante = position;
        this.destination = destination;
    }

    @Override
    public void onClick(View v){

        activity.afficherChemin(position_courante, destination);
        this.hide();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_dialog_activity);
        this.bouton = (ImageButton) findViewById(R.id.boutonIniténaire);
        this.bouton.setImageResource(R.drawable.ic_place_black_24dp);
        this.bouton.setOnClickListener(this);
    }

}
