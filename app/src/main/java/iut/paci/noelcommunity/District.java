package iut.paci.noelcommunity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Damien_Admin2 on 10/02/2017.
 */

public class District implements Serializable{

    private int id, imageResourceId;
    private String name, description;
    private float longitude, latitude;
    List<Store> stores;
    List<Deposite> deposites;

    public District(int id, String name, String description, float longitude, float latitude, int imageResourceId){
        this.id = id;
        this.name = name;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imageResourceId = imageResourceId;
        this.stores = new ArrayList<>();
        this.deposites = new ArrayList<>();
    }

    public String getTag() {
        if(id == 1)
            return id + "er";
        else
            return id + "ème";
    }

    @Override
    public String toString() {
        return getTag() + " arrondissement : " + name;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((District) obj).id;
    }


    public static District fromJson(String json){
        Gson gson = new GsonBuilder().setDateFormat("HH:mm:ss").create();
        District district = gson.fromJson(json, District.class);
        return district;
    }

    public static List<District> fromJsonList(String json){
        Gson gson = new GsonBuilder().setDateFormat("HH:mm:ss").create();
        Type type = new TypeToken<List<District>>(){}.getType();
        List<District> districts = (List<District>) gson.fromJson(json, type);
        return districts;
    }


    public static String toJson(District district){
        Gson gson = new GsonBuilder().setDateFormat("HH:mm:ss").create();
        String json = gson.toJson(district);
        return json;
    }

    public List<Deposite> getDeposites() {
        return deposites;
    }

    public List<Store> getStores() {
        return stores;
    }

    public String toDialog() {
        return description
                + "\n\n"
                + "Position : (" + longitude + ";" + latitude + ")";
    }

    public String getName() {
        return name;
    }

    public float getLongitude() {

        return longitude;
    }

    public float getLatitude() {

        return latitude;
    }

    public String getDescription() {

        return description;
    }

    public int getImageResourceId() {

        return imageResourceId;
    }

    public int getId() {

        return id;
    }
}
