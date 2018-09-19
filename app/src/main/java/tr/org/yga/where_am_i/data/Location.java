package tr.org.yga.where_am_i.data;

import android.support.annotation.Nullable;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Location {
    @Nullable
    @SerializedName("cc")
    private String cc;
    @Nullable
    @SerializedName("country")
    private String country;
    @Nullable
    @SerializedName("labeledLatLngs")
    private List<LabeledLatLngsItem> labeledLatLngs;

    @SerializedName("lng")
    private double lng;

    @SerializedName("distance")
    private int distance;

    public int getDistance(){
        return this.distance;
    }
    @Nullable
    @SerializedName("formattedAddress")
    private List<String> formattedAddress;

    @SerializedName("lat")
    private double lat;
    @Nullable
    @SerializedName("address")
    private String address;
    @Nullable
    @SerializedName("city")
    private String city;
    @Nullable
    @SerializedName("postalCode")
    private String postalCode;
    @Nullable
    @SerializedName("state")
    private String state;
    @Nullable
    @SerializedName("crossStreet")
    private String crossStreet;
}