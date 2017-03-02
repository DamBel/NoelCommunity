package iut.paci.noelcommunity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ahmed on 13/02/2017.
 */
public class Deposite extends Place implements Serializable {

    int emptyCount;

    public Deposite(int id, String name, double longitude, double latitude, Date openingTime, Date closingTime, int emptyCount, int districtId) {
        super(id, name, longitude, latitude, openingTime, closingTime, districtId);
        this.emptyCount = emptyCount;
    }

    @Override
    public String toString() {
        return name + "(" + emptyCount + ")";
    }

}
