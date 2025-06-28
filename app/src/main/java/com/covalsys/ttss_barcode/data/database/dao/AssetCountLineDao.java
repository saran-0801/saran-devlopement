package com.covalsys.ttss_barcode.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.ttss_barcode.data.database.entities.AssetCountLine;

import java.util.List;

@Dao
public interface AssetCountLineDao {

    @Query("SELECT * FROM AssetCountLine Order by Status DESC")
    LiveData<List<AssetCountLine>> getData();

    @Query("SELECT * FROM AssetCountLine")
    List<AssetCountLine> getDataList();

    @Query("SELECT * FROM AssetCountLine where Status = 'P'")
    List<AssetCountLine> getPendingDataList();

    @Query("SELECT count(*) FROM AssetCountLine")
    int getDataSize();

    @Query("SELECT count(*) FROM AssetCountLine where Status = 'A'")
    int getScannedDataSize();

    @Query("SELECT count(*) FROM AssetCountLine where Status = 'P'")
    int getPendingDataCount();

    @Query("SELECT slno FROM AssetCountLine where slno =:lineNo ")
    int getLineNo(int lineNo);

    @Query("SELECT Status FROM AssetCountLine where ItemCode =:itemCode")
    String getLineStatus(String itemCode);

    @Query("SELECT count(*) FROM AssetCountLine")
    int dataCount();

    @Query("SELECT EXISTS (SELECT 1 FROM AssetCountLine WHERE ItemCode =:id)")
    int isAddToCart(String id);

    @Query("SELECT count(*) FROM AssetCountLine where DocNum =:docNum")
    int dataCount(String docNum);

    @Query("SELECT COUNT(ItemCode) FROM AssetCountLine")
    LiveData<Integer> getRowCount();

    @Query("SELECT COUNT(slno) from AssetCountLine where slno = (SELECT max(slno) FROM AssetCountScanLine)")
    int tableExistNo();

    @Query("UPDATE AssetCountLine set ActQty =:actQty, Remarks =:remark where slno =:lineNo")
    void updateBatchInfo(int lineNo, Float actQty, String remark);

    @Query("UPDATE AssetCountLine set Status ='A', ActQty = SysQty, ScanDate =:date  where ItemCode =:itemCode")
    void updateStatus(String itemCode, String date);

    @Insert
    void add(AssetCountLine assetCountLine);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<AssetCountLine> assetCountLines);

    @Query("DELETE FROM AssetCountLine")
    void deleteAllData();

    @Query("DELETE FROM AssetCountLine where slno =:slno")
    void deleteData(int slno);
}
