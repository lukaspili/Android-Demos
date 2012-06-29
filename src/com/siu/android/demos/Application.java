package com.siu.android.demos;

import android.content.Context;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class Application extends android.app.Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
