package tr.org.yga.where_am_i.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;
import tr.org.yga.where_am_i.data.FoursquareResponse;


// eğer category secerlerse sectiona atayabilirsin , o zman explore methpdu kullan
public interface ApiInterface{
    // TODO: intenti browse yap böylece radius tanımlayabilrsin
    @GET("venues/search")
    Call<FoursquareResponse> search(@Query("ll") String latlon, @Query("client_id") String client_id,@Query("client_secret") String client_secret,@Query("intent") String intent, @Query("v") int date, @Query("radius") int radius,@Query("limit")int limit);

    @GET("venues/search")
    Call<FoursquareResponse> searchByCategory(@Query("ll") String latlon,@Query("client_id") String client_id,@Query("client_secret") String client_secret,@Query("intent") String intent, @Query("v") int date,@Query("radius") int radius,@Query("limit") int limit,@Query("categoryId") String categoryId);

    @GET("venues/explore")
    Call<FoursquareResponse>  explore(@Query("ll") String latlon, @Query("client_id") String client_id,@Query("client_secret") String client_secret,@Query("section") String section, @Query("v") int date, @Query("limit") int limit);


}


