package iut.paci.noelcommunity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ahmed on 13/02/2017.
 */

public abstract class Place implements Serializable {

    int id;
    String name;
    double longitude;
    double latitude;
    Date openingTime;
    Date closingTime;
    int districtId;

    public Place(int id, String name, double longitude, double latitude, Date openingTime, Date closingTime, int districtId) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.districtId = districtId;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getName() { return name; }
}
