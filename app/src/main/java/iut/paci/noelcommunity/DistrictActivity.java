package iut.paci.noelcommunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DistrictActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ArrayList<District> liste = this.getData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        MyAdapter adapter = new MyAdapter(this, R.layout.activity_grid_image_layout, liste);
        gridView.setAdapter(adapter);

        //ImageView IV = (ImageView) findViewById(R.id.img);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                MyDialog dialog = new MyDialog(DistrictActivity.this, liste.get(position).getName());

                //dialog.getImageBouton().setOnClickListener(dialog);

                dialog.show();

                /*dialog.getImageBouton().setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DistrictActivity.this, MapActivity.class);
                        startActivity(intent);
                    }
                });*/

            }
        });

    }

    private ArrayList<District> getData() {

        ArrayList<District> items = new ArrayList<>();

        items.add(new District(1, "Louvre", "", 0f, 0f, R.drawable.img_district1));
        items.add(new District(2, "Bourse", "", 0f, 0f, R.drawable.img_district2));
        items.add(new District(3, "Temple", "", 0f, 0f, R.drawable.img_district3));
        items.add(new District(4, "Hôtel-de-Ville", "", 0f, 0f, R.drawable.img_district4));
        items.add(new District(5, "Panthéon", "", 0f, 0f, R.drawable.img_district5));
        items.add(new District(6, "Luxembourg", "", 0f, 0f, R.drawable.img_district6));
        items.add(new District(7, "Palais-Bourbon", "", 0f, 0f, R.drawable.img_district7));
        items.add(new District(8, "Elysée", "", 0f, 0f, R.drawable.img_district8));
        items.add(new District(9, "Opéra", "", 0f, 0f, R.drawable.img_district9));
        items.add(new District(10, "Enclos Saint-Laurent", "", 0f, 0f, R.drawable.img_district10));
        items.add(new District(11, "Popincourt", "", 0f, 0f, R.drawable.img_district11));
        items.add(new District(12, "Reuilly", "", 0f, 0f, R.drawable.img_district12));
        items.add(new District(13, "Gobelins", "", 0f, 0f, R.drawable.img_district13));
        items.add(new District(14, "Observatoire", "", 0f, 0f, R.drawable.img_district14));
        items.add(new District(15, "Vaugirard", "", 0f, 0f, R.drawable.img_district15));
        items.add(new District(16, "Passy", "", 0f, 0f, R.drawable.img_district16));
        items.add(new District(17, "Batignolles-Monceau", "", 0f, 0f, R.drawable.img_district17));
        items.add(new District(18, "Buttes-Montmartre", "", 0f, 0f, R.drawable.img_district18));
        items.add(new District(19, "Buttes-Chaumont", "", 0f, 0f, R.drawable.img_district19));
        items.add(new District(20, "Ménilmontant", "", 0f, 0f, R.drawable.img_district20));

        return items;
    }
}