package iut.paci.noelcommunity;

import java.io.Serializable;

/**
 * Created by Damien_Admin2 on 10/02/2017.
 */

public class Contact implements Serializable {

    private String nom;
    private String mdp;

    public Contact(String nom, String mdp){
        this.nom = nom;
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public String getMdp() {
        return mdp;
    }
}
