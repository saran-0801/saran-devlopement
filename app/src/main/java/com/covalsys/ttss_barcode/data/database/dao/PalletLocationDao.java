package com.covalsys.ttss_barcode.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.ttss_barcode.data.database.entities.PalletLocation;

import java.util.List;

/**
 * Created by CBS on 03-09-2020.
 */
@Dao
public interface PalletLocationDao {

    @Query("SELECT * FROM PalletLocation")
    LiveData<List<PalletLocation>> getData();

    @Insert
    void add(PalletLocation... pallets);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<PalletLocation> pallets);

    @Query("SELECT COUNT(PalletLocationCode) FROM PalletLocation")
    LiveData<Integer> getRowCount();

    @Query("SELECT COUNT(PalletLocationCode) FROM PalletLocation")
    int getCount();

    @Query("SELECT COUNT(PalletLocationCode) from InwardMaster where slno = (SELECT max(slno) FROM PalletLocation)")
    int tableExistNo();

    @Query("SELECT slno from InwardMaster where slno = (SELECT max(slno) FROM PalletLocation)")
    int updatedLocationValue();

    @Query("SELECT EXISTS (SELECT 1 FROM PalletLocation WHERE PalletLocationCode =:id)")
    int isAddToCart(String id);

    @Query("UPDATE PalletLocation set Flag = '1' where Flag = '0'")
    void updateFlagDraft();

    @Query("UPDATE PalletLocation set Flag = '-1' where Flag = '0' AND slno = :id")
    void updateCheckBoxFlag(int id);

    /*@Query("DELETE FROM PalletLocation WHERE Flag = '-1'")
    void deleteSelectedAllData();*/

    @Query("DELETE FROM PalletLocation WHERE slno = :id")
    void deleteData(Integer id);

    @Query("DELETE FROM PalletLocation")
    void deleteAllData();
}



