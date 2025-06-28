package com.covalsys.ttss_barcode.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.ttss_barcode.data.database.entities.AssetCountScanLine;

import java.util.List;

@Dao
public interface AssetCountScanLineDao {

    @Query("SELECT * FROM AssetCountScanLine order by slno desc")
    LiveData<List<AssetCountScanLine>> getData();

    @Query("SELECT * FROM AssetCountScanLine")
    List<AssetCountScanLine> getDataList();

    @Query("SELECT count(*) FROM AssetCountScanLine")
    int getDataSize();

    @Insert
    void add(AssetCountScanLine... assetCountScanLines);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<AssetCountScanLine> assetCountScanLines);

    @Query("SELECT EXISTS (SELECT 1 FROM AssetCountScanLine WHERE Itemcode =:id)")
    int isAddToCart(String id);

    @Query("DELETE FROM AssetCountScanLine")
    void deleteAllData();

    @Query("DELETE FROM AssetCountScanLine where slno =:slno")
    void deleteData(int slno);
}
