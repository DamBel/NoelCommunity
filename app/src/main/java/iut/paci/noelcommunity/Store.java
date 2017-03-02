package iut.paci.noelcommunity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ahmed on 13/02/2017.
 */
public class Store extends Place implements Serializable{

    int treeCount;

    public Store(int id, String name, double longitude, double latitude, Date openingTime, Date closingTime, int treeCount, int districtId) {
        super(id,name,longitude, latitude, openingTime, closingTime, districtId);
        this.treeCount = treeCount;

    }

    @Override
    public String toString() {
        return name + "(" + treeCount + ")";
    }
}
