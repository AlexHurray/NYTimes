package com.example.ermolaenkoalex.nytimes;

import android.app.Application;

import com.example.ermolaenkoalex.nytimes.di.DbModule;
import com.example.ermolaenkoalex.nytimes.di.NetworkModule;

import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.configuration.Configuration;
import toothpick.registries.FactoryRegistryLocator;
import toothpick.registries.MemberInjectorRegistryLocator;
import toothpick.smoothie.module.SmoothieApplicationModule;

public class AppDelegate extends Application {

    private static Scope sAppScope;

    @Override
    public void onCreate() {
        super.onCreate();

        Toothpick.setConfiguration(Configuration.forProduction().disableReflection());
        MemberInjectorRegistryLocator.setRootRegistry(new com.example.ermolaenkoalex.nytimes.MemberInjectorRegistry());
        FactoryRegistryLocator.setRootRegistry(new com.example.ermolaenkoalex.nytimes.FactoryRegistry());

        sAppScope = Toothpick.openScope(AppDelegate.class);
        sAppScope.installModules(new SmoothieApplicationModule(this), new DbModule(this), new NetworkModule());
    }

    public static Scope getAppScope() {
        return sAppScope;
    }
}
