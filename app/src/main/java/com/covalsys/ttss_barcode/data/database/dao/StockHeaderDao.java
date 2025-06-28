package com.covalsys.ttss_barcode.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.ttss_barcode.data.database.entities.StockHeader;

import java.util.List;

@Dao
public interface StockHeaderDao {

    @Query("SELECT * FROM StockHeader")
    LiveData<List<StockHeader>> getData();

    @Query("SELECT * FROM StockHeader")
    List<StockHeader> getDataList();

    @Query("SELECT Status FROM StockHeader limit 1")
    String getStatus();

    @Insert
    void add(StockHeader... stockHeaders);

    @Query("DELETE FROM StockHeader")
    void deleteAllData();

}



