package iut.paci.noelcommunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreerCompteActivity extends AppCompatActivity {

    private final int mini_pseudo = 5;
    private final int max_pseudo = 15;

    EditText pseudo, mdp, mdp_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);

        pseudo = (EditText) findViewById(R.id.pseudo);
        mdp = (EditText) findViewById(R.id.mdp);
        mdp_confirm = (EditText) findViewById(R.id.mdp_confirm);
        Button valider = (Button) findViewById(R.id.boutonValider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pseudo.getText().toString() == null || pseudo.getText().toString().equals("")){
                    Toast.makeText(v.getContext(), "Aucun pseudo renseigné", Toast.LENGTH_LONG).show();
                } else if (pseudo.getText().toString().length() < mini_pseudo) {
                    Toast.makeText(v.getContext(), "Taille du pseudo trop courte : 5 caractères mini", Toast.LENGTH_LONG).show();
                } else if (pseudo.getText().toString().length() > max_pseudo) {
                    Toast.makeText(v.getContext(), "Taille du pseudo trop longue : 15 caractères max", Toast.LENGTH_LONG).show();
                }else if (mdp.getText().toString() == null || mdp.getText().toString().equals("")){
                    Toast.makeText(v.getContext(), "Mot de passe non renseigné", Toast.LENGTH_LONG).show();
                }else if (mdp_confirm.getText().toString() == null || mdp_confirm.getText().toString().equals("") || !(mdp.getText().toString().equals(mdp_confirm.getText().toString()))){
                    Toast.makeText(v.getContext(), "Les mots de passe ne correspondent pas", Toast.LENGTH_LONG).show();
                } else {
                    Contact contact = new Contact(pseudo.getText().toString(), mdp.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("contact", contact);
                    Intent intent = new Intent(CreerCompteActivity.this, LoginActivity.class);
                    intent.putExtras(bundle);
                    Toast.makeText(v.getContext(), "Création de compte réussie!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }

            }
        });

    }
}
