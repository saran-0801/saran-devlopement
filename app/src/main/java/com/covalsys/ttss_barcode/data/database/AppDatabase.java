package com.covalsys.ttss_barcode.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.covalsys.ttss_barcode.data.database.dao.AssetCountLineDao;
import com.covalsys.ttss_barcode.data.database.dao.AssetCountScanLineDao;
import com.covalsys.ttss_barcode.data.database.dao.AssetHeaderDao;
import com.covalsys.ttss_barcode.data.database.dao.InwardMasterDao;
import com.covalsys.ttss_barcode.data.database.dao.OutPalletDao;
import com.covalsys.ttss_barcode.data.database.dao.OutPalletLocationDao;
import com.covalsys.ttss_barcode.data.database.dao.OutwardMasterDao;
import com.covalsys.ttss_barcode.data.database.dao.PalletDao;
import com.covalsys.ttss_barcode.data.database.dao.PalletLocationDao;
import com.covalsys.ttss_barcode.data.database.dao.StockCountLineDao;
import com.covalsys.ttss_barcode.data.database.dao.StockCountScanLineDao;
import com.covalsys.ttss_barcode.data.database.dao.StockHeaderDao;
import com.covalsys.ttss_barcode.data.database.entities.AssetCountLine;
import com.covalsys.ttss_barcode.data.database.entities.AssetCountScanLine;
import com.covalsys.ttss_barcode.data.database.entities.AssetHeader;
import com.covalsys.ttss_barcode.data.database.entities.InwardMaster;
import com.covalsys.ttss_barcode.data.database.entities.OutPallet;
import com.covalsys.ttss_barcode.data.database.entities.OutPalletLocation;
import com.covalsys.ttss_barcode.data.database.entities.OutwardMaster;
import com.covalsys.ttss_barcode.data.database.entities.Pallet;
import com.covalsys.ttss_barcode.data.database.entities.PalletLocation;
import com.covalsys.ttss_barcode.data.database.entities.StockCountLine;
import com.covalsys.ttss_barcode.data.database.entities.StockCountScanLine;
import com.covalsys.ttss_barcode.data.database.entities.StockHeader;

/**
 * Created by Prasanth Muthu on 19-07-2023.
 */
@Database(entities = {InwardMaster.class, OutwardMaster.class, Pallet.class, PalletLocation.class,
        OutPallet.class, OutPalletLocation.class, StockCountLine.class, StockCountScanLine.class, StockHeader.class , AssetHeader.class , AssetCountLine.class, AssetCountScanLine.class}, version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "DB_TTSS";
    private static AppDatabase mInstance;

    public synchronized static AppDatabase getDatabaseInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }

    public abstract InwardMasterDao inwardMasterDao();

    public abstract OutwardMasterDao outwardMasterDao();

    public abstract PalletDao palletDao();

    public abstract PalletLocationDao palletLocationDao();

    public abstract OutPalletDao outPalletDao();

    public abstract OutPalletLocationDao outPalletLocationDao();

    public abstract StockCountLineDao stockCountLineDao();

    public abstract StockCountScanLineDao stockCountScanLineDao();

    public abstract StockHeaderDao stockHeaderDao();

    public abstract AssetHeaderDao assetHeaderDao();

    public abstract AssetCountLineDao assetCountLineDao();

    public abstract AssetCountScanLineDao assetCountScanLineDao();

}
