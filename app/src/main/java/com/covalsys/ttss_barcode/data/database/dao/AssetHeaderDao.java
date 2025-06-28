package com.covalsys.ttss_barcode.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.covalsys.ttss_barcode.data.database.entities.AssetHeader;
import java.util.List;

@Dao
public interface AssetHeaderDao {

    @Query("SELECT * FROM AssetHeader")
    LiveData<List<AssetHeader>> getData();

    @Query("SELECT * FROM AssetHeader")
    List<AssetHeader> getDataList();

    @Query("SELECT * FROM AssetHeader Order by slno DESC limit 1")
    AssetHeader getSingleData();

    @Query("SELECT Status FROM AssetHeader limit 1")
    String getStatus();

    @Insert
    void add(AssetHeader... assetHeaders);

    @Query("DELETE FROM AssetHeader")
    void deleteAllData();
}
