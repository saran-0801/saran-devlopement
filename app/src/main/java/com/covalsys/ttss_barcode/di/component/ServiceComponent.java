package com.covalsys.ttss_barcode.di.component;

import com.covalsys.ttss_barcode.di.PerService;
import com.covalsys.ttss_barcode.di.module.ServiceModule;

import dagger.Component;


@PerService
@Component(dependencies = AppComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

   //void inject(LocationService service);
}
