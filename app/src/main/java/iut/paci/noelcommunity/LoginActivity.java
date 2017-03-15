package iut.paci.noelcommunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //Toujours appeler la méthode de la classe super
        setContentView(R.layout.activity_login);

        final Button monBouton = (Button) findViewById(R.id.button);
        final Button boutonCréerCompte = (Button) findViewById(R.id.button2);
        final EditText etId = (EditText) findViewById(R.id.editText);
        final EditText etMdp = (EditText) findViewById(R.id.editText2);

        etId.setText("abcd");
        etMdp.setText("efgh");

        monBouton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                String identifiant = etId.getText().toString();
                String mdp = etMdp.getText().toString();

                if (identifiant.equals("") || mdp.equals("")){
                    Toast.makeText(v.getContext(), "Identifiant et/ou mot de passe non renseigné", Toast.LENGTH_LONG).show();
                }
                else if (identifiant.equals("abcd") && mdp.equals("efgh")){
                    Toast.makeText(v.getContext(), "Connecté!", Toast.LENGTH_SHORT).show();

                    Contact personne = new Contact(identifiant, mdp);

                    //Dans une fonction on ne met pas 'this' mais '...Activity.this'
                    Intent successIntent = new Intent(LoginActivity.this, DistrictActivity.class);
                    Bundle extra = new Bundle();
                    extra.putSerializable("personne", personne);
                    successIntent.putExtras(extra);
                    startActivity(successIntent);
                }
                else{
                    Toast.makeText(v.getContext(), "Identifiant et/ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                }

            }
        });

        boutonCréerCompte.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, CreerCompteActivity.class);
                startActivity(intent);

            }
        });

    }
}
