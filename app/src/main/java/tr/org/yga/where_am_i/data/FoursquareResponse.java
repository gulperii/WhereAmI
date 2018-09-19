package tr.org.yga.where_am_i.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class FoursquareResponse{
   @Nullable
	@SerializedName("meta")
	private Meta meta;

   @Nullable
	@SerializedName("response")
	private Response response;

	@Nullable
	public Response getResponse() {
		return response;
	}
}