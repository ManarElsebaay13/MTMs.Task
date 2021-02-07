package eg.com.MtMs.mtmstask.app;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

import java.util.Locale;

public class MTMsApp extends Application {
    private static Context appContext;
    private static MTMsApp mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        // setLocalenew();
        mInstance = this;

    }
    public static Context getAppContext() {
        return appContext;
    }

}
