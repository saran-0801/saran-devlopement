package com.covalsys.ttss_barcode.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.ttss_barcode.data.database.entities.StockCountLine;

import java.util.List;

@Dao
public interface StockCountLineDao {

    @Query("SELECT * FROM StockCountLine Order by Status DESC")
    LiveData<List<StockCountLine>> getData();

    @Query("SELECT * FROM StockCountLine")
    List<StockCountLine> getDataList();

    @Query("SELECT * FROM StockCountLine where Status = 'P'")
    List<StockCountLine> getPendingDataList();

    @Query("SELECT count(*) FROM StockCountLine")
    int getDataSize();

    @Query("SELECT count(*) FROM StockCountLine where Status = 'A'")
    int getScannedDataSize();

    @Query("SELECT count(*) FROM StockCountLine where Status = 'P'")
    int getPendingDataCount();

    @Query("SELECT slno FROM StockCountLine where slno =:lineNo ")
    int getLineNo(int lineNo);

    @Query("SELECT Status FROM StockCountLine where ItemCode =:itemCode")
    String getLineStatus(String itemCode);

    @Query("SELECT count(*) FROM StockCountLine")
    int dataCount();

    @Query("SELECT EXISTS (SELECT 1 FROM StockCountLine WHERE ItemCode =:id)")
    int isAddToCart(String id);

    @Query("SELECT count(*) FROM StockCountLine where DocNum =:docNum")
    int dataCount(String docNum);

    @Query("SELECT COUNT(ItemCode) FROM StockCountLine")
    LiveData<Integer> getRowCount();

    @Query("SELECT COUNT(slno) from StockCountLine where slno = (SELECT max(slno) FROM StockCountScanLine)")
    int tableExistNo();

    @Query("UPDATE StockCountLine set ActQty =:actQty, Remarks =:remark where slno =:lineNo")
    void updateBatchInfo(int lineNo, Float actQty, String remark);

    @Query("UPDATE StockCountLine set Status ='A', ActQty = SysQty, ScanDate =:date  where ItemCode =:itemcode")
    void updateStatus(String itemcode, String date);

    @Insert
    void add(StockCountLine... stockCountLines);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<StockCountLine> stockCountLines);

    @Query("DELETE FROM StockCountLine")
    void deleteAllData();

    @Query("DELETE FROM StockCountLine where slno =:slno")
    void deleteData(int slno);
}



