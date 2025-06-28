package com.covalsys.ttss_barcode.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.ttss_barcode.data.database.entities.OutPalletLocation;

import java.util.List;

/**
 * Created by CBS on 03-09-2020.
 */
@Dao
public interface OutPalletLocationDao {

    @Query("SELECT * FROM PalletLocation")
    LiveData<List<OutPalletLocation>> getData();

    @Insert
    void add(OutPalletLocation... pallets);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<OutPalletLocation> pallets);

    @Query("SELECT COUNT(PalletLocationCode) FROM OutPalletLocation")
    LiveData<Integer> getRowCount();

    @Query("SELECT COUNT(PalletLocationCode) FROM OutPalletLocation")
    int getCount();

    @Query("SELECT COUNT(PalletLocationCode) from OutwardMaster where slno = (SELECT max(slno) FROM OutPalletLocation)")
    int tableExistNo();

    @Query("SELECT slno from OutwardMaster where slno = (SELECT max(slno) FROM OutPalletLocation)")
    int updatedLocationValue();

    @Query("SELECT EXISTS (SELECT 1 FROM OutPalletLocation WHERE PalletLocationCode =:id)")
    int isAddToCart(String id);

    @Query("UPDATE OutPalletLocation set Flag = '1' where Flag = '0'")
    void updateFlagDraft();

    @Query("UPDATE OutPalletLocation set Flag = '-1' where Flag = '0' AND slno = :id")
    void updateCheckBoxFlag(int id);

    @Query("DELETE FROM OutPalletLocation WHERE Flag = '-1'")
    void deleteData();

    @Query("DELETE FROM OutPalletLocation WHERE slno = :id")
    void deleteData(Integer id);

    @Query("DELETE FROM OutPalletLocation")
    void deleteAllData();
}



