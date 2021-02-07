package eg.com.MtMs.mtmstask.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import eg.com.MtMs.mtmstask.R;
import eg.com.MtMs.mtmstask.data.dialogData.DialogListener;

import static eg.com.MtMs.mtmstask.utils.AppKey.MY_PERMISSIONS_REQUEST_LOCATION;

public class StaticMethods {


    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static boolean checkCameraPermission(Context context, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
            else if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
            else  if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
            else
                return  true ;
        }else{
            return  true  ;
        }
    }

    public static void ShowSnake(CoordinatorLayout _context, Context context, String message) {
        Snackbar snackbar = Snackbar
                .make(_context, message, Snackbar.LENGTH_LONG);
        int color;
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        color = Color.WHITE;
        textView.setTextColor(color);
        textView.setTextSize(18);
        textView.setGravity(Gravity.CENTER);
        snackbar.show();
    }
    public static void NOConnecting(CoordinatorLayout _context, Context context) {
        Snackbar snackbar = Snackbar
                .make(_context, context.getString(R.string.noconnection), Snackbar.LENGTH_LONG);
        int color;
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        color = Color.RED;
        textView.setTextColor(color);
        textView.setTextSize(18);
        textView.setGravity(Gravity.CENTER);
        snackbar.show();
    }


    public static void setFragment(Activity activity, Fragment fragment, int contenerFragment, String tag) {
        FragmentTransaction fragmentTransaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(contenerFragment, fragment, tag);
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }


    // map
    public  static boolean  checkLocationPermission
    (Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }



    public static boolean getGPSStatus(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    public static void displaydialougGps(final Context context) {
        new DialogPoP.Builder(context)
                .setTitle(context.getString(R.string.gps))
                .setMessage(context.getString(R.string.Gpsneed))
                .setNegativeBtnText("no")
                .setPositiveBtnText("yes")
                .isCancellable(true)
                .OnPositiveClicked(new DialogListener() {
                    @Override
                    public void OnClick() {
                        context.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));

                    }
                })
                .OnNegativeClicked(new DialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();

    }











    }
