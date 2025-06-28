package com.covalsys.ttss_barcode.data.database;

import androidx.lifecycle.LiveData;

import com.covalsys.ttss_barcode.data.database.entities.InwardMaster;
import com.covalsys.ttss_barcode.data.database.entities.OutPallet;
import com.covalsys.ttss_barcode.data.database.entities.OutPalletLocation;
import com.covalsys.ttss_barcode.data.database.entities.OutwardMaster;
import com.covalsys.ttss_barcode.data.database.entities.Pallet;
import com.covalsys.ttss_barcode.data.database.entities.PalletLocation;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.internal.operators.completable.CompletableFromAction;


@Singleton
public class RoomDataManager implements RoomHelper{

    private AppDatabase mDatabase;

    @Inject
    public RoomDataManager(AppDatabase appDatabase) {
        this.mDatabase = appDatabase;
    }

    @Override
    public LiveData<List<InwardMaster>> getAllInwardData() {
        return mDatabase.inwardMasterDao().getData();
    }

    @Override
    public Flowable<InwardMaster> getInwardDataList(String id) {
        return null;
    }

    @Override
    public Completable insertInwardData(InwardMaster... data) {
        return new CompletableFromAction(() -> {
            mDatabase.inwardMasterDao().add(data);
        });
    }

    @Override
    public Completable insertOrUpdateInwardData(List<InwardMaster> InwardData) {
        return new CompletableFromAction(() -> {
            mDatabase.inwardMasterDao().insertOrUpdateData(InwardData);
        });
    }

    @Override
    public Completable deleteAllInwardData() {
        return new CompletableFromAction(() -> {
            mDatabase.inwardMasterDao().deleteAllData();
        });
    }

    @Override
    public LiveData<List<OutwardMaster>> getAllOutwardDataList() {
        return null;
    }

    @Override
    public Flowable<OutwardMaster> getOutwardDataList(String id) {
        return null;
    }

    @Override
    public Completable insertOutwardDataMaster(OutwardMaster... data) {
        return null;
    }

    @Override
    public Completable insertOrUpdateOutwardDataMaster(List<OutwardMaster> brands) {
        return null;
    }

    @Override
    public Completable deleteAllOutwardDataMaster() {
        return null;
    }

    @Override
    public LiveData<List<Pallet>> getAllPallet() {
        return mDatabase.palletDao().getData();
    }

    @Override
    public Flowable<Pallet> getPallet(String id) {
        return null;
    }

    @Override
    public Completable insertPallet(Pallet... data) {
        return new CompletableFromAction(() -> {
            mDatabase.palletDao().add(data);
        });
    }

    @Override
    public Completable insertOrUpdatePallet(List<Pallet> pallets) {
        return new CompletableFromAction(() -> {
            mDatabase.palletDao().insertOrUpdateData(pallets);
        });
    }

    @Override
    public Completable deleteAllPallet() {
        return new CompletableFromAction(() -> {
            mDatabase.palletDao().deleteAllData();
        });
    }

    @Override
    public LiveData<List<PalletLocation>> getAllPalletLocation() {
        return mDatabase.palletLocationDao().getData();
    }

    @Override
    public Flowable<PalletLocation> getPalletLocation(String id) {
        return null;
    }

    @Override
    public Completable insertPalletLocation(PalletLocation... data) {
        return new CompletableFromAction(() -> {
            mDatabase.palletLocationDao().add(data);
        });
    }

    @Override
    public Completable insertOrUpdatePalletLocation(List<PalletLocation> pallets) {
        return new CompletableFromAction(() -> {
            mDatabase.palletLocationDao().insertOrUpdateData(pallets);
        });
    }

    @Override
    public Completable deleteAllPalletLocation() {
        return new CompletableFromAction(() -> {
            mDatabase.palletLocationDao().deleteAllData();
        });
    }

    @Override
    public LiveData<List<InwardMaster>> getAllData() {
        return mDatabase.palletDao().getPostData();
    }

    @Override
    public LiveData<List<OutPallet>> getAllOutPallet() {
        return mDatabase.outPalletDao().getData();
    }

    @Override
    public Flowable<OutPallet> getOutPallet(String id) {
        return null;
    }

    @Override
    public Completable insertOutPallet(OutPallet... data) {
        return new CompletableFromAction(() -> {
            mDatabase.outPalletDao().add(data);
        });
    }

    @Override
    public Completable insertOrUpdateOutPallet(List<OutPallet> pallets) {
        return new CompletableFromAction(() -> {
            mDatabase.outPalletDao().insertOrUpdateData(pallets);
        });
    }

    @Override
    public Completable deleteAllOutPallet() {
        return new CompletableFromAction(() -> {
            mDatabase.outPalletDao().deleteAllData();
        });
    }

    @Override
    public LiveData<List<OutPalletLocation>> getAllOutPalletLocation() {
        return mDatabase.outPalletLocationDao().getData();
    }

    @Override
    public Flowable<OutPalletLocation> getOutPalletLocation(String id) {
        return null;
    }

    @Override
    public Completable insertOutPalletLocation(OutPalletLocation... data) {
        return new CompletableFromAction(() -> {
            mDatabase.outPalletLocationDao().add(data);
        });
    }

    @Override
    public Completable insertOrUpdateOutPalletLocation(List<OutPalletLocation> pallets) {
        return new CompletableFromAction(() -> {
            mDatabase.outPalletLocationDao().insertOrUpdateData(pallets);
        });
    }

    @Override
    public Completable deleteAllOutPalletLocation() {
        return new CompletableFromAction(() -> {
            mDatabase.outPalletLocationDao().deleteAllData();
        });
    }

    @Override
    public LiveData<List<OutwardMaster>> getAllOutData() {
        return mDatabase.outPalletDao().getPostData();
    }
}
