package com.corydominguez.gator;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by coryd on 16/02/2014.
 */
public class GatorApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        GatorApp.context = this;

        // Create global configuration and initialize ImageLoader with this configuration
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
