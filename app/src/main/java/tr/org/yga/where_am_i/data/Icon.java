package tr.org.yga.where_am_i.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Icon {
    @Nullable
    @SerializedName("prefix")
    private String prefix;
    @Nullable
    @SerializedName("suffix")
    private String suffix;
}