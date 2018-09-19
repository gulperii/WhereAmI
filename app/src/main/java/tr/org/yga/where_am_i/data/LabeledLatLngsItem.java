package tr.org.yga.where_am_i.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class LabeledLatLngsItem {

    @SerializedName("lng")
    private double lng;

    @Nullable
    @SerializedName("label")
    private String label;

    @SerializedName("lat")
    private double lat;
}