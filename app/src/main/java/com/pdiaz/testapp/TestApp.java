package com.pdiaz.testapp;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pdiaz.testapp.ApiServices.ApiLiverpool;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by diiaz94 on 04-04-2017.
 */
public class TestApp extends Application {

    private ApiLiverpool apiLiverpool;

    @Override
    public void onCreate(){
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLiverpool.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiLiverpool =  retrofit.create(ApiLiverpool.class);
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(getApplicationContext());

        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public ApiLiverpool getApiLiverpoolIntance() {
        return apiLiverpool;
    }
}
