package tr.org.yga.where_am_i.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class CategoriesItem {

    @Nullable
    @SerializedName("pluralName")
    private String pluralName;
    @Nullable
    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private Icon icon;
    @Nullable
    @SerializedName("id")
    private String id;
    @Nullable
    @SerializedName("shortName")
    private String shortName;

    @SerializedName("primary")
    private boolean primary;

    @Nullable
    public String getPluralName() {
        return pluralName;
    }


    @Nullable
    public String getName() {
        return name;
    }



    public Icon getIcon() {
        return icon;
    }


    @Nullable
    public String getId() {
        return id;
    }



    @Nullable
    public String getShortName() {
        return shortName;
    }



    public boolean isPrimary() {
        return primary;
    }


}