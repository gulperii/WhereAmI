package tr.org.yga.where_am_i.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("code")
    private int code;
    @Nullable
    @SerializedName("requestId")
    private String requestId;
}