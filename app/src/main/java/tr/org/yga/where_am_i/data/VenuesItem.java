package tr.org.yga.where_am_i.data;

import android.support.annotation.Nullable;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class VenuesItem {

    @SerializedName("hasPerk")
    private boolean hasPerk;
    @Nullable
    @SerializedName("referralId")
    private String referralId;
    @Nullable
    @SerializedName("name")
    public String name;
    @Nullable
    @SerializedName("location")
    private Location location;
public Location getLocation(){
    return this.location;
}

    @Nullable
    @SerializedName("id")
    private String id;
    @Nullable
    @SerializedName("categories")
    private List<CategoriesItem> categories;

    @Nullable
    public String getName() {
        return this.name;
    }
}