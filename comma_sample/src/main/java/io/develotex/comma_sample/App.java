package io.develotex.comma_sample;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;


public class App extends Application {

    private static App mInstance;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static App getInstance() {
        return mInstance;
    }

}