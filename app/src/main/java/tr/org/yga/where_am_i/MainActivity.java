// TODO: categorize poi?
package tr.org.yga.where_am_i;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.org.yga.where_am_i.api.ApiClient;
import tr.org.yga.where_am_i.api.ApiInterface;
import tr.org.yga.where_am_i.data.FoursquareResponse;
import tr.org.yga.where_am_i.data.VenuesItem;

public class MainActivity extends AppCompatActivity {
    //TODO:tagi doğrula
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<String> selectedCategoryIds = new ArrayList<>();

    final ArrayList<Integer> mSelectedItems = new ArrayList<>();
    private static final int PERMISSION_REQUEST_FINE_LOCATION = 101;
    private static final long TWO_MINUTES = 2 * 60 * 1000;
    private Location mLastLocation;
    private Location currentBestLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private int GPS_SETTING_REQUEST = 102;
    private com.google.android.gms.location.LocationListener listener;
    private FusedLocationProviderClient mFusedLocationClient;
    private List<VenuesItem> poiList;
    private List<VenuesItem> poiListWithCategories = new ArrayList<VenuesItem>();;
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult.getLocations().size() == 0) {
                return;
            } else {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Location newLocation = locationResult.getLastLocation();
                if (newLocation.getLongitude() != currentBestLocation.getLongitude()&&newLocation.getLatitude() != currentBestLocation.getLatitude()) {
                    currentBestLocation = getBetterLocation(newLocation, currentBestLocation);
                    if(mSelectedItems.isEmpty()){
                    Call<FoursquareResponse> search = apiService.search(currentBestLocation.getLatitude() + "," + currentBestLocation.getLongitude(), "AP02AR5H3RU5SOGUTGODE1TEJ2GOYQQP34LBFS0FNPY3UVQX", "R1GWVVZH4GM0NP3ONDMCVDXQDIDX11KQDWSUIXJ2FQTBIVHY", "checkin", 20180913, 500, 50);
                    search.enqueue(new Callback<FoursquareResponse>() {
                        @Override
                        public void onResponse(Call<FoursquareResponse> call, Response<FoursquareResponse> response) {
                            poiList = response.body().getResponse().getVenues();
                            while (poiList.isEmpty()) {
                                poiList = response.body().getResponse().getVenues();
                            }
                            bubbleSort(poiList, poiList.size());
                            TextView text = (TextView) findViewById(R.id.textView);
                            text.setText(poiList.get(0).getName() + " " + poiList.get(0).getLocation().getDistance() + " metre");
                            text = (TextView) findViewById(R.id.textView2);
                            text.setText(poiList.get(1).getName() + " " + poiList.get(1).getLocation().getDistance() + " metre");
                            text = (TextView) findViewById(R.id.textView3);
                            text.setText(poiList.get(2).getName() + " " + poiList.get(2).getLocation().getDistance() + " metre");
                            text = (TextView) findViewById(R.id.textView4);
                            text.setText(poiList.get(3).getName() + " " + poiList.get(3).getLocation().getDistance() + " metre");
                            text = (TextView) findViewById(R.id.textView5);
                            text.setText(poiList.get(4).getName() + " " + poiList.get(4).getLocation().getDistance() + " metre");
                        }
                        @Override
                        public void onFailure(Call<FoursquareResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });}
                    else if(!mSelectedItems.isEmpty()){
                        for(String category_id: selectedCategoryIds) {
                            Call<FoursquareResponse> searchByCategory = apiService.searchByCategory(currentBestLocation.getLatitude() + "," + currentBestLocation.getLongitude(), "AP02AR5H3RU5SOGUTGODE1TEJ2GOYQQP34LBFS0FNPY3UVQX", "R1GWVVZH4GM0NP3ONDMCVDXQDIDX11KQDWSUIXJ2FQTBIVHY", "checkin", 20180913, 6000, 50, category_id);
                            searchByCategory.enqueue(new Callback<FoursquareResponse>() {
                                @Override
                                public void onResponse(Call<FoursquareResponse> call, Response<FoursquareResponse> response) {
                                    if(!response.body().getResponse().getVenues().isEmpty()) {
                                        poiListWithCategories.clear();
                                        poiListWithCategories.addAll(response.body().getResponse().getVenues());
                                    }
                                    while (poiListWithCategories.isEmpty()) {
                                        poiListWithCategories.addAll(response.body().getResponse().getVenues());
                                    }
                                    bubbleSort(poiListWithCategories, poiListWithCategories.size());
                                    TextView text = (TextView) findViewById(R.id.textView);
                                    text.setText(poiListWithCategories.get(0).getName() + " " + poiListWithCategories.get(0).getLocation().getDistance() + " metre");
                                    text = (TextView) findViewById(R.id.textView2);
                                    text.setText(poiListWithCategories.get(1).getName() + " " + poiListWithCategories.get(1).getLocation().getDistance() + " metre");
                                    text = (TextView) findViewById(R.id.textView3);
                                    text.setText(poiListWithCategories.get(2).getName() + " " + poiListWithCategories.get(2).getLocation().getDistance() + " metre");
                                    text = (TextView) findViewById(R.id.textView4);
                                    text.setText(poiListWithCategories.get(3).getName() + " " + poiListWithCategories.get(3).getLocation().getDistance() + " metre");
                                    text = (TextView) findViewById(R.id.textView5);
                                    text.setText(poiListWithCategories.get(4).getName() + " " + poiListWithCategories.get(4).getLocation().getDistance() + " metre");
                                }

                                @Override
                                public void onFailure(Call<FoursquareResponse> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }

                    }
                    Log.e(TAG, "onLocationResult: " + currentBestLocation);
                }
            }
        }
    };


    void bubbleSort(List<VenuesItem> unsortedFoursquare, int n) {

        if (n == 1)
            return;

        for (int i = 0; i < n - 1; i++)
            if (unsortedFoursquare.get(i).getLocation().getDistance() > unsortedFoursquare.get(i + 1).getLocation().getDistance()) {

                Collections.swap(unsortedFoursquare, i, i + 1);
            }

        bubbleSort(unsortedFoursquare, n - 1);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final Button button = findViewById(R.id.press);
        final Button button2 = findViewById(R.id.stop);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button.setVisibility(View.GONE);
                button2.setVisibility(View.VISIBLE);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stopLocationUpdates();

                    }
                });
                Dialog categorySelection = onCreateDialog(savedInstanceState);
                categorySelection.show();


            }
        });


    }


    private void gpsPrompt() {

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("You should turn on your GPS to use this app. Press OK to go to settings");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent gpsOptionsIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(gpsOptionsIntent, GPS_SETTING_REQUEST);

                    }
                });

        alertDialog.show();
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GPS_SETTING_REQUEST) {
            if (resultCode == RESULT_OK) {
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location
                                if (location != null) {
                                    mLastLocation = location;
                                    Toast.makeText(MainActivity.this, "Longitude: " + mLastLocation.getLongitude() + "Latitude: " + mLastLocation.getLatitude(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                mLocationRequest = new LocationRequest();
                //TODO:optimize
                mLocationRequest.setInterval(TWO_MINUTES / 2); // 12 sn interval
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());
            }
        }
    }


    @SuppressLint("MissingPermission")
    private void listenLocation() {
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsPrompt();
        } else if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location
                            if (location != null) {
                                mLastLocation = location;
                                currentBestLocation = mLastLocation;
                                Toast.makeText(MainActivity.this, "Longitude: " + mLastLocation.getLongitude() + "Latitude: " + mLastLocation.getLatitude(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(TWO_MINUTES/4); // 12 sn interval
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());
        }
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @TargetApi(Build.VERSION_CODES.M)

    private void askLocalPermission() {
        int hasLocalPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasLocalPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
        } else {

            listenLocation();
        }
    }

    Map<String, String> categoriesWithId = new HashMap<String, String>() {{
        put("Sanat & Eğlence", "4d4b7104d754a06370d81259");
        put("Üniversite", "4d4b7105d754a06372d81259");
        put("Yemek", "4d4b7105d754a06374d81259");
        put("Gece Hayatı Noktası", "4d4b7105d754a06376d81259");
        put("Açık Alanlar ve Dinlence", "4d4b7105d754a06377d81259");
        put("Profesyonel & Diğer Yerler", "4d4b7105d754a06375d81259");
        put("Mağaza & Hizmet", "4d4b7105d754a06378d81259");
        put("Seyahat & Taşıma", "4d4b7105d754a06379d81259");

    }};


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] category = {"Sanat & Eğlence", "Üniversite", "Yemek", "Gece Hayatı Noktası", "Açık Alanlar ve Dinlence", "Profesyonel & Diğer Yerler", "Mağaza & Hizmet", "Seyahat & Taşıma"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Pick Categories")
                .setMultiChoiceItems(category, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(which);

                        } else if (mSelectedItems.contains(which)) {
                            mSelectedItems.remove(which);
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                for (int index : mSelectedItems) {
                    selectedCategoryIds.add(categoriesWithId.get(category[index]));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    askLocalPermission();
                } else {

                    listenLocation();
                }
            }
        });

        return builder.create();
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_FINE_LOCATION:
                if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    // Permission Granted
                    listenLocation();
                } else {
                    // Permission Denied
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    /**
     * Determines whether one Location reading is better than the current Location fix.
     * Code taken from
     * http://developer.android.com/guide/topics/location/obtaining-user-location.html
     *
     * @param newLocation         The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new
     * @return The better Location object based on recency and accuracy.
     * Taken from google android guide
     */
    protected Location getBetterLocation(Location newLocation, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return newLocation;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES / 2;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES / 2;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved.
        if (isSignificantlyNewer) {
            return newLocation;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return currentBestLocation;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;


        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return newLocation;
        } else if (isNewer && !isLessAccurate) {
            return newLocation;
        } else if (isNewer && !isSignificantlyLessAccurate) {
            return newLocation;
        }
        return currentBestLocation;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
}