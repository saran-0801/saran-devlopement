package com.covalsys.ttss_barcode.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.ttss_barcode.data.database.entities.StockCountScanLine;

import java.util.List;

@Dao
public interface StockCountScanLineDao {

    @Query("SELECT * FROM StockCountScanLine order by slno desc")
    LiveData<List<StockCountScanLine>> getData();

    @Query("SELECT * FROM StockCountScanLine")
    List<StockCountScanLine> getDataList();

    @Query("SELECT count(*) FROM StockCountScanLine")
    int getDataSize();

    @Insert
    void add(StockCountScanLine... stockCountScanLines);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<StockCountScanLine> stockCountScanLines);

    @Query("SELECT EXISTS (SELECT 1 FROM StockCountScanLine WHERE Itemcode =:id)")
    int isAddToCart(String id);

    @Query("DELETE FROM StockCountScanLine")
    void deleteAllData();

    @Query("DELETE FROM StockCountScanLine where slno =:slno")
    void deleteData(int slno);
}



