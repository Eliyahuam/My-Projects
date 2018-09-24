package com.example.eliyahugalfinal.picapp.Model;

import android.app.Application;
import android.content.Context;

/**
 * Created by eliba on 30/03/2017.
 */

public class AppContext extends Application {
    private static Context context;
    public void onCreate() {
        super.onCreate();
        AppContext.context = getApplicationContext();
    }
    public static Context getAppContext() {
        return AppContext.context;
    }
}
