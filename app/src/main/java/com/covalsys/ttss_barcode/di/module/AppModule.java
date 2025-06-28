package com.covalsys.ttss_barcode.di.module;

import android.app.Application;
import android.content.Context;

import com.covalsys.ttss_barcode.BuildConfig;
import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomDataManager;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.data.network.RequestInterceptor;
import com.covalsys.ttss_barcode.data.network.repository.Repository;
import com.covalsys.ttss_barcode.data.preferences.PreferenceHelper;
import com.covalsys.ttss_barcode.data.preferences.PreferencesManager;
import com.covalsys.ttss_barcode.di.AppContext;
import com.covalsys.ttss_barcode.di.PreferenceInfo;
import com.covalsys.ttss_barcode.ui.asset_tag.AssetCountingAdapter;
import com.covalsys.ttss_barcode.ui.inward.InwardLocationScannedDataAdapter;
import com.covalsys.ttss_barcode.ui.inward.InwardPalletScannedDataAdapter;
import com.covalsys.ttss_barcode.ui.inward.InwardScannedDataAdapter;
import com.covalsys.ttss_barcode.ui.outward.OutwardScannedDataAdapter;
import com.covalsys.ttss_barcode.ui.stock_counting.StockCountingAdapter;
import com.covalsys.ttss_barcode.utils.Constants;
import com.covalsys.ttss_barcode.utils.rx.AppSchedulerProvider;
import com.covalsys.ttss_barcode.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = ViewModelModule.class)
public class AppModule {

    @Singleton
    @Provides
    @AppContext
    Context provideContext(Application application){
        return application;
    }

    /*@Singleton
    @Provides
    LocationService provideLocationService(Application application){
        return new LocationService(provideContext(application));
    }*/

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(5, TimeUnit.MINUTES);
        okHttpClient.readTimeout(5, TimeUnit.MINUTES);
        okHttpClient.writeTimeout(5, TimeUnit.MINUTES);
        okHttpClient.addInterceptor(new RequestInterceptor());
        okHttpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    ApiService provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ApiService.class);
    }


    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Application application) {
        return AppDatabase.getDatabaseInstance(application);
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return Constants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferencesHelper(PreferencesManager preferencesManager) {
        return preferencesManager;
    }

    @Provides
    @Singleton
    RoomHelper provideRoomHelper (RoomDataManager dataManager) {
        return dataManager;
    }

    @Provides
    @Singleton
    Repository providesRepository(ApiService apiService){
        return new Repository(apiService);
    }

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    InwardPalletScannedDataAdapter providesPalletAdapter(Application application){
        return new InwardPalletScannedDataAdapter(new ArrayList<>(),provideContext(application));
    }

    @Provides
    @Singleton
    InwardLocationScannedDataAdapter providesLocationAdapter(Application application){
        return new InwardLocationScannedDataAdapter(new ArrayList<>(),provideContext(application));
    }

    @Provides
    @Singleton
    InwardScannedDataAdapter providesAdapter(Application application){
        return new InwardScannedDataAdapter(new ArrayList<>(),provideContext(application));
    }

    @Provides
    @Singleton
    OutwardScannedDataAdapter providesOutwardAdapter(Application application){
        return new OutwardScannedDataAdapter(new ArrayList<>(),provideContext(application));
    }

    @Provides
    @Singleton
    StockCountingAdapter providesStockCountingAdapter(Application application){
        return new StockCountingAdapter(new ArrayList<>(),provideContext(application));
    }

    @Provides
    @Singleton
    AssetCountingAdapter providesAssetCountingAdapter(Application application){
        return new AssetCountingAdapter(new ArrayList<>(),provideContext(application));
    }

}
