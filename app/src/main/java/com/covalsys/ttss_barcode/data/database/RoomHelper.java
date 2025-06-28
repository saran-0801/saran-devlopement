package com.covalsys.ttss_barcode.data.database;

import androidx.lifecycle.LiveData;

import com.covalsys.ttss_barcode.data.database.entities.InwardMaster;
import com.covalsys.ttss_barcode.data.database.entities.OutPallet;
import com.covalsys.ttss_barcode.data.database.entities.OutPalletLocation;
import com.covalsys.ttss_barcode.data.database.entities.OutwardMaster;
import com.covalsys.ttss_barcode.data.database.entities.Pallet;
import com.covalsys.ttss_barcode.data.database.entities.PalletLocation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


public interface RoomHelper {

    /**
     * Inward from room start
     */
    LiveData<List<InwardMaster>> getAllInwardData();


    Flowable<InwardMaster> getInwardDataList(String id);


    Completable insertInwardData(InwardMaster... data);


    Completable insertOrUpdateInwardData(List<InwardMaster> InwardData);


    Completable deleteAllInwardData();

    /*
     InwardData master end
     */

    /**
     * OutWard from room start
     */
    LiveData<List<OutwardMaster>> getAllOutwardDataList();


    Flowable<OutwardMaster> getOutwardDataList(String id);


    Completable insertOutwardDataMaster(OutwardMaster... data);


    Completable insertOrUpdateOutwardDataMaster(List<OutwardMaster> brands);


    Completable deleteAllOutwardDataMaster();

    /*
     * OutwardData end
     */

    /**
     * Pallet from room start
     */
    LiveData<List<Pallet>> getAllPallet();


    Flowable<Pallet> getPallet(String id);


    Completable insertPallet(Pallet... data);


    Completable insertOrUpdatePallet(List<Pallet> pallets);


    Completable deleteAllPallet();

    /*
     * Pallet end
     */

    /**
     * Pallet location from room start
     */
    LiveData<List<PalletLocation>> getAllPalletLocation();


    Flowable<PalletLocation> getPalletLocation(String id);


    Completable insertPalletLocation(PalletLocation... data);


    Completable insertOrUpdatePalletLocation(List<PalletLocation> pallets);


    Completable deleteAllPalletLocation();

    LiveData<List<InwardMaster>> getAllData();

    /*
     * Pallet Location end
     */

    /**
     * OutPallet from room start
     */
    LiveData<List<OutPallet>> getAllOutPallet();


    Flowable<OutPallet> getOutPallet(String id);


    Completable insertOutPallet(OutPallet... data);


    Completable insertOrUpdateOutPallet(List<OutPallet> pallets);


    Completable deleteAllOutPallet();

    /*
     * OutPallet end
     */

    /**
     * Out Pallet Location from room start
     */
    LiveData<List<OutPalletLocation>> getAllOutPalletLocation();


    Flowable<OutPalletLocation> getOutPalletLocation(String id);


    Completable insertOutPalletLocation(OutPalletLocation... data);


    Completable insertOrUpdateOutPalletLocation(List<OutPalletLocation> pallets);


    Completable deleteAllOutPalletLocation();

    LiveData<List<OutwardMaster>> getAllOutData();

    /*
     * Out Pallet Location end
     */

}
