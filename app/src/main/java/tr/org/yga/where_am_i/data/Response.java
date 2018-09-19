package tr.org.yga.where_am_i.data;

import android.support.annotation.Nullable;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("confident")
    private boolean confident;
    @Nullable
    @SerializedName("venues")
    private List<VenuesItem> venues;

    @Nullable
    public List<VenuesItem> getVenues() {
        return venues;
    }
}