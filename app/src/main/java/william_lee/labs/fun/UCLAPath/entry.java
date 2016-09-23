package william_lee.labs.fun.UCLAPath;

/**
 * Created by william_lee on 9/12/16.
 */
public class entry {

    private String description;
    private double latitude;
    private double longitude;
    private String datetime;
    private String status;

    public entry(String description, double latitude, double longitude, String datetime){
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.datetime = datetime;
    }
    public String getDescription(){
        return this.description;
    }
    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String s) {
        this.status = s;
    }
}
