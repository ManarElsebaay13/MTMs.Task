package eg.com.MtMs.mtmstask.gui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eg.com.MtMs.mtmstask.R;
import eg.com.MtMs.mtmstask.adapter.DestinationAdapter;
import eg.com.MtMs.mtmstask.adapter.SourceAdapter;
import eg.com.MtMs.mtmstask.base_class.BaseActivity;
import eg.com.MtMs.mtmstask.data.DestinationLocation;
import eg.com.MtMs.mtmstask.data.SourceLocation;
import eg.com.MtMs.mtmstask.gui.Main_Page.MainPageActivity;
import eg.com.MtMs.mtmstask.utils.AppKey;
import eg.com.MtMs.mtmstask.utils.Dummydata;
import eg.com.MtMs.mtmstask.utils.StaticMethods;
import eg.com.MtMs.mtmstask.utils.ToastUtil;

import static eg.com.MtMs.mtmstask.utils.AppConstant.DESTINATION_LOCETION_LIST;
import static eg.com.MtMs.mtmstask.utils.AppConstant.DESTINATION_NAME;
import static eg.com.MtMs.mtmstask.utils.AppConstant.SOURCE_LOCETION_LIST;
import static eg.com.MtMs.mtmstask.utils.StaticMethods.checkLocationPermission;
import static eg.com.MtMs.mtmstask.utils.StaticMethods.displaydialougGps;
import static eg.com.MtMs.mtmstask.utils.StaticMethods.getGPSStatus;
import static eg.com.MtMs.mtmstask.utils.StaticMethods.isConnectingToInternet;

public class MapActivity extends BaseActivity implements Map_View,OnMapReadyCallback, LocationListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    //,.m,.,m.,./,/,0000.
    Button confirm;
    private GoogleMap mmap;
    private FrameLayout frameLayout;
    private SupportMapFragment mapFragment;
    private static final String TAG = "MapActivity";
    CameraPosition currentcameraPosition;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private Context mContext = MapActivity.this;
    String AddressName = "";
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;

    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    LatLng lat_long;
    String street =null ;

    @BindView(R.id.lin_notification)
    LinearLayout lin_notification;
    @BindView(R.id.coorHome)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.Menu_icon)
    ImageView Menu_icon;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.Locations_Rv)
    RecyclerView Locations_Rv;
    @BindView(R.id.pin_view_circle)
    FrameLayout  pin_view_circle;
    @BindView(R.id.Requested)
    Button Requested;
    Dummydata dummydata;
    SourceAdapter sourceAdapter;
    DestinationAdapter destinationAdapter;
    @BindView(R.id.yourLocation)
    EditText yourLocation;
    @BindView(R.id.destinationLocation)
    EditText destinationLocation;
    @BindView(R.id.locations_linear)
    LinearLayout locations_linear;


    @OnClick(R.id.Menu_icon)
    void Menu() {
        openDrawer();
    }
    @OnClick(R.id.back_icon)
    void back() { ResetUI();}
    @OnClick(R.id.lin_notification)
    void click() { closeDrawer(); }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.maps_activity);
        ButterKnife.bind(this);


        boolean internetAvailable = StaticMethods.isConnectingToInternet(this);
        if (!internetAvailable) {
            showNoNetworkConnectionBase(coordinatorLayout, this);
            return;
        }

        if(!StaticMethods.checkLocationPermission(this,MapActivity.this)){
        }else if (!StaticMethods.getGPSStatus(this)){
            StaticMethods.displaydialougGps(this);
        }


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);


        getLocationPermission();
        getDeviceLocation();
        initViews();
        getDataFireStore();


    }




    private void initViews() {

        frameLayout = findViewById(R.id.map_container);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        yourLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                UpdateUISL();
            }
        });

        destinationLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                UpdateUIDL();
            }
        });

        Requested.setOnClickListener(v -> {
            finish();
            if (lat_long!=null){
                AddressName = getAddress(lat_long.latitude, lat_long.longitude);
                Log.d("mylocation",AddressName);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(AppKey.MAPLatitude, lat_long.latitude);
                    returnIntent.putExtra(AppKey.MAPLongtitude, lat_long.longitude);
                    returnIntent.putExtra(AppKey.MAPAddress, AddressName);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();


            }else {
                ToastUtil.showErrorToast(MapActivity.this, R.string.loading);
            }

        });

    }

    private void getLocationPermission() {

        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initViews();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initViews();
                }
            }
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mmap = googleMap;
        if (mLocationPermissionsGranted) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                return;
            }
            mmap.setMyLocationEnabled(true);
            mmap.getUiSettings().setMyLocationButtonEnabled(false);

        }


        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

                currentcameraPosition = cameraPosition;

                Log.i("centerLat", String.valueOf(cameraPosition.target.latitude));

                Log.i("centerLong", String.valueOf(cameraPosition.target.longitude));
                lat_long = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);

            }
        });

    }

    private String getAddress(double latitude, double longitude) {

        Geocoder geocoder;
        List <Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()){
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();

                 street = addresses.get(0).getThoroughfare();


                return state + "," + country;
            }else {
                return "";
            }


        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void getDeviceLocation() {

        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {
                Task <Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(new OnCompleteListener <Location>() {
                    @Override
                    public void onComplete(@NonNull Task <Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Location location = task.getResult();

                            if (location != null) {
                                LatLng currentLatLng = new LatLng(location.getLatitude(),
                                        location.getLongitude());


                                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng,
                                        DEFAULT_ZOOM);
                                mmap.moveCamera(update);
                            }

                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
    }


    @Override
    public void onLocationChanged(Location location) {

        try {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (latLng != null) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM);
                mmap.animateCamera(cameraUpdate);
                locationManager.removeUpdates(this);

            }

        } catch (Exception e) {

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void ResetUI(){
        Menu_icon.setVisibility(View.VISIBLE);
        back_icon.setVisibility(View.GONE);
        pin_view_circle.setVisibility(View.VISIBLE);
        Requested.setVisibility(View.VISIBLE);
        Locations_Rv.setVisibility(View.GONE);

    }


    public void UpdateUISL()
    {
        Menu_icon.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);
        pin_view_circle.setVisibility(View.GONE);
        Requested.setVisibility(View.GONE);
        Locations_Rv.setVisibility(View.VISIBLE);

        dummydata=new Dummydata();

        Locations_Rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        sourceAdapter =new SourceAdapter(SOURCE_LOCETION_LIST, this);
        Locations_Rv.setAdapter(sourceAdapter);

    }

    public void UpdateUIDL()
    {
        Menu_icon.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);
        pin_view_circle.setVisibility(View.GONE);
        Requested.setVisibility(View.GONE);
        Locations_Rv.setVisibility(View.VISIBLE);

        dummydata=new Dummydata();

        Locations_Rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        destinationAdapter =new DestinationAdapter(DESTINATION_LOCETION_LIST, this);
        Locations_Rv.setAdapter(destinationAdapter);

    }

    public void setSourceName(String name){yourLocation.setText(name);}
    public void setDestinationName(String name){destinationLocation.setText(name);}


    public void getDataFireStore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Source")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            SOURCE_LOCETION_LIST = new ArrayList <>();

                            for (DocumentSnapshot document : task.getResult()) {

                                SourceLocation taskItem = document.toObject(SourceLocation.class);

                                SOURCE_LOCETION_LIST.add(taskItem);
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        db.collection("Destination")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            DESTINATION_LOCETION_LIST = new ArrayList <>();

                            for (DocumentSnapshot document : task.getResult()) {

                                DestinationLocation taskItem = document.toObject(DestinationLocation.class);
                                DESTINATION_LOCETION_LIST.add(taskItem);
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    private void openDrawer() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(MapActivity.this.INPUT_METHOD_SERVICE);
        View focusedView = this.getWindow().getDecorView().getRootView();
        inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
}

    private void closeDrawer(){

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


}



