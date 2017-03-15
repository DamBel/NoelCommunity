package iut.paci.noelcommunity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Map;

/**
 * Created by Damien_Admin2 on 22/02/2017.
 */

public class MyDialog extends Dialog implements View.OnClickListener{

    Activity activity;
    String titre;
    ImageButton bouton;
    District district;

    public MyDialog(Activity activity, String titre, District district){
        super(activity);
        this.activity = activity;
        this.titre = titre;
        this.district = district;
    }

    @Override
    public void onClick(View v){

        Intent i = new Intent(this.activity, MapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("district", district);
        i.putExtras(bundle);
        activity.startActivity(i);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);
        setTitle(this.titre);
        this.bouton = (ImageButton) findViewById(R.id.ImageBouton);
        this.bouton.setImageResource(R.drawable.ic_place_black_24dp);
        this.bouton.setOnClickListener(this);
    }

}
