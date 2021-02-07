package eg.com.MtMs.mtmstask.gui.Main_Page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import eg.com.MtMs.mtmstask.R;
import eg.com.MtMs.mtmstask.base_class.BaseActivity;
import eg.com.MtMs.mtmstask.gui.home.MapActivity;
import eg.com.MtMs.mtmstask.gui.menu_fragment.MenuFragment;
import eg.com.MtMs.mtmstask.utils.FragmentHandler;
import eg.com.MtMs.mtmstask.utils.IntentUtiles;
import eg.com.MtMs.mtmstask.utils.StaticMethods;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static eg.com.MtMs.mtmstask.utils.AppKey.HOME_FRAGMENT;
import static eg.com.MtMs.mtmstask.utils.AppKey.MENU_FRAGMENT;
import static eg.com.MtMs.mtmstask.utils.StaticMethods.checkLocationPermission;
import static eg.com.MtMs.mtmstask.utils.StaticMethods.displaydialougGps;
import static eg.com.MtMs.mtmstask.utils.StaticMethods.getGPSStatus;

public class MainPageActivity extends BaseActivity {


    @BindView(R.id.coormainpage)
    CoordinatorLayout coorHome;


    int fragmentPostion;
    public int selectedPosition=0;
    public int selectedPositionInside = 0 ;
    public  static int selected=0;
    Bundle bundle = null;

    private static final int RC_LOCATION_REQUEST = 1234;
    private int RC_LOCATION_ON_REQUEST = 1235;
    private LocationRequest locationRequest;



    @Override
    protected void onResume() { super.onResume(); }

    @Override
    protected void onPause() {
        super.onPause();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

    }
    private void initView() {



        displayView(HOME_FRAGMENT);



    }

    public void displayView(int position) {


        switch (position) {

            case HOME_FRAGMENT:


               IntentUtiles.openActivityInNewStack(MainPageActivity.this, MapActivity.class);
                break;

//            case MENU_FRAGMENT :
//                FragmentHandler.displayView(MENU_FRAGMENT, this, new MenuFragment(), R.id.main_content);
//                break;


        }}


    @Override
    public void onBackPressed() {
        selectedPosition =   FragmentHandler.getSelectPoistion();
        selectedPositionInside=FragmentHandler.getSelectedPositionInside();

        if (selectedPosition>=MENU_FRAGMENT)
        { displayView(HOME_FRAGMENT);}

        else if(selectedPosition==HOME_FRAGMENT||selectedPositionInside==HOME_FRAGMENT)
        { finish(); }

        else {

            super.onBackPressed();
        }
    }
    //setbundle
    public void SetBundle(Bundle bundle, int View_page) {
        this.bundle = bundle;
        displayView(View_page);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }






}