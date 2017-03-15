package iut.paci.noelcommunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DistrictActivity extends AppCompatActivity {

    private ArrayList<District> update = new ArrayList<District>();
    MyAdapter adapter;
    GridView gridView;
    final ArrayList<District> liste = this.getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);

        EditText rechercher = (EditText) findViewById(R.id.rechercherDistrict);

        adapter = new MyAdapter(DistrictActivity.this, R.layout.activity_grid_image_layout, liste);

        gridView = (GridView) findViewById(R.id.gridView);

        gridView.setAdapter(adapter);

        rechercher.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                update.clear();
                String search = s.toString();

                for (District d : liste){

                    if (d.getName().toLowerCase().contains(search.toLowerCase())){

                        update.add(d);

                    }

                    adapter = new MyAdapter(DistrictActivity.this, R.layout.activity_grid_image_layout, update);

                    gridView.setAdapter(adapter);

                }

            }
        });

        //MyAdapter adapter = new MyAdapter(this, R.layout.activity_grid_image_layout, update);

        //gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                MyDialog dialog = new MyDialog(DistrictActivity.this, liste.get(position).getName(), liste.get(position));

                dialog.show();
            }
        });

    }

    private ArrayList<District> getData() {

        ArrayList<District> items = new ArrayList<>();

        items.add(new District(1, "Louvre", "", 48.8592, 2.3417, R.drawable.img_district1));
        items.add(new District(2, "Bourse", "", 48.8655, 2.3426, R.drawable.img_district2));
        items.add(new District(3, "Temple", "", 48.8637, 2.3615, R.drawable.img_district3));
        items.add(new District(4, "Hôtel-de-Ville", "", 48.8601, 2.3507, R.drawable.img_district4));
        items.add(new District(5, "Panthéon", "", 48.8448, 2.3471, R.drawable.img_district5));
        items.add(new District(6, "Luxembourg", "", 48.8493, 2.33, R.drawable.img_district6));
        items.add(new District(7, "Palais-Bourbon", "", 48.8565, 2.321, R.drawable.img_district7));
        items.add(new District(8, "Elysée", "", 48.8763, 2.3183, R.drawable.img_district8));
        items.add(new District(9, "Opéra", "", 48.8718, 2.3399, R.drawable.img_district9));
        items.add(new District(10, "Enclos Saint-Laurent", "", 48.8709, 2.3561, R.drawable.img_district10));
        items.add(new District(11, "Popincourt", "", 48.8574, 2.3795, R.drawable.img_district11));
        items.add(new District(12, "Reuilly", "", 48.8412, 2.3876, R.drawable.img_district12));
        items.add(new District(13, "Gobelins", "", 48.8322, 2.3561, R.drawable.img_district13));
        items.add(new District(14, "Observatoire", "", 48.8331, 2.3264, R.drawable.img_district14));
        items.add(new District(15, "Vaugirard", "", 48.8412, 2.3003, R.drawable.img_district15));
        items.add(new District(16, "Passy", "", 48.8637, 2.2769, R.drawable.img_district16));
        items.add(new District(17, "Batignolles-Monceau", "", 48.8835, 2.3219, R.drawable.img_district17));
        items.add(new District(18, "Buttes-Montmartre", "", 48.8925, 2.3444, R.drawable.img_district18));
        items.add(new District(19, "Buttes-Chaumont", "", 48.8817, 2.3822, R.drawable.img_district19));
        items.add(new District(20, "Ménilmontant", "", 48.8646, 2.3984, R.drawable.img_district20));

        return items;
    }
}