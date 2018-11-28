package com.example.ermolaenkoalex.nytimes;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;

import com.example.ermolaenkoalex.nytimes.di.DbModule;
import com.example.ermolaenkoalex.nytimes.di.NetworkModule;
import com.example.ermolaenkoalex.nytimes.service.NewsUpdateWorker;
import com.example.ermolaenkoalex.nytimes.utils.NetworkUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.configuration.Configuration;
import toothpick.registries.FactoryRegistryLocator;
import toothpick.registries.MemberInjectorRegistryLocator;
import toothpick.smoothie.module.SmoothieApplicationModule;

public class AppDelegate extends Application {
    private static Scope appScope;

    @Inject
    @NonNull
    NetworkUtils networkUtils;

    @Override
    public void onCreate() {
        super.onCreate();

        Toothpick.setConfiguration(Configuration.forProduction().disableReflection());
        MemberInjectorRegistryLocator.setRootRegistry(new com.example.ermolaenkoalex.nytimes.MemberInjectorRegistry());
        FactoryRegistryLocator.setRootRegistry(new com.example.ermolaenkoalex.nytimes.FactoryRegistry());

        appScope = Toothpick.openScope(AppDelegate.class);
        appScope.installModules(new SmoothieApplicationModule(this), new DbModule(this), new NetworkModule(this));

        Toothpick.inject(this, appScope);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().build(), networkUtils.getNetworkCallback());
        }

        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        String TAG_UPDATE = "com.example.ermolaenkoalex.nytimes.TAG_UPDATE";
        PeriodicWorkRequest work = new PeriodicWorkRequest.Builder(NewsUpdateWorker.class, 180, TimeUnit.MINUTES)
                .addTag(TAG_UPDATE)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance().enqueueUniquePeriodicWork(TAG_UPDATE, ExistingPeriodicWorkPolicy.REPLACE, work);
    }

    public static Scope getAppScope() {
        return appScope;
    }
}
