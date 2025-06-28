package com.covalsys.ttss_barcode.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.ttss_barcode.data.database.entities.InwardMaster;

import java.util.List;

/**
 * Created by CBS on 03-09-2020.
 */
@Dao
public interface InwardMasterDao {

    @Query("SELECT slno, ifnull(PalletCode,'') PalletCode, ifnull(PalletLocationCode, '') PalletLocationCode, ChBox FROM InwardMaster where Flag = '0'")
    LiveData<List<InwardMaster>> getData();

    @Insert
    void add(InwardMaster... inwardMasters);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<InwardMaster> inwardMasters);

    @Query("UPDATE InwardMaster set PalletCode = :pallet where slno = :id")
    void updatePallet(String pallet, int id);

    @Query("UPDATE InwardMaster set PalletLocationCode = :palletLocation where slno = :id")
    void updatePalletLocation(String palletLocation, int id);

    @Query("UPDATE InwardMaster set PalletLocationCode = :palletLocation where PalletLocationCode = ''")
    void updatePalletLocationEmpty(String palletLocation);

    @Query("SELECT COUNT(PalletCode) from InwardMaster where PalletLocationCode = ''")
    int countPalletLocationEmpty();

    @Query("SELECT COUNT(PalletCode) FROM InwardMaster")
    LiveData<Integer> getRowCount();

    @Query("UPDATE InwardMaster set Flag = '1' where Flag = '0'")
    void updateFlagDraft();

    @Query("UPDATE InwardMaster set ChBox = 'Ture' where Flag = '0' AND slno = :id")
    void updateCheckBoxFlag(int id);

    @Query("DELETE FROM InwardMaster WHERE ChBox = 'True'")
    void deleteSelectedAllData();

    @Query("DELETE FROM InwardMaster WHERE slno = :id")
    void deleteData(Integer id);

    @Query("DELETE FROM InwardMaster")
    void deleteAllData();
}



