package com.covalsys.ttss_barcode.di.component;

import android.app.Application;

import com.covalsys.ttss_barcode.MyApplication;
import com.covalsys.ttss_barcode.di.builder.ActivityBuilder;
import com.covalsys.ttss_barcode.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by CBS on 09-07-2020.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

  //  void inject(LocationService service);

    void inject(MyApplication myApplication);
}
