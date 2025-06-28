package com.covalsys.ttss_barcode.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.ttss_barcode.data.database.entities.OutPallet;
import com.covalsys.ttss_barcode.data.database.entities.OutwardMaster;

import java.util.List;

/**
 * Created by CBS on 03-09-2020.
 */
@Dao
public interface OutPalletDao {

    @Query("SELECT * FROM OutPallet")
    LiveData<List<OutPallet>> getData();

    @Insert
    void add(OutPallet... pallets);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<OutPallet> pallets);

    @Query("SELECT COUNT(PalletCode) FROM OutPallet")
    LiveData<Integer> getRowCount();

    @Query("SELECT COUNT(PalletCode) FROM OutPallet")
    int getCount();

    @Query("SELECT COUNT(PalletCode) from OutwardMaster where slno = (SELECT max(slno) FROM OutPallet)")
    int tableExistNo();

    @Query("SELECT slno from OutwardMaster where slno = (SELECT max(slno) FROM OutPallet)")
    int updatedLocationValue();

    @Query("SELECT a.slno, a.PalletCode, b.PalletLocationCode FROM OutPallet a inner join OutPalletLocation b on a.slno = b.slno")
    LiveData<List<OutwardMaster>> getPostData();

    @Query("SELECT a.slno, a.PalletCode, b.PalletLocationCode FROM OutPallet a inner join OutPalletLocation b on a.slno = b.slno")
    List<OutwardMaster> postDataToServer();

    @Query("SELECT EXISTS (SELECT 1 FROM OutPallet WHERE PalletCode =:id)")
    int isAddToCart(String id);

    @Query("UPDATE OutPallet set Flag = '1' where Flag = '0'")
    void updateFlagDraft();

    @Query("UPDATE OutPallet set Flag = '-1' where Flag = '0' AND slno = :id")
    void updateCheckBoxFlag(int id);

    @Query("DELETE FROM OutPallet WHERE Flag = '-1'")
    void deleteData();

    @Query("DELETE FROM OutPallet WHERE slno = :id")
    void deleteData(Integer id);

    @Query("DELETE FROM OutPallet")
    void deleteAllData();
}



