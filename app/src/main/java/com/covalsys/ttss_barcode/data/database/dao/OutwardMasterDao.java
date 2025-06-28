package com.covalsys.ttss_barcode.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.ttss_barcode.data.database.entities.OutwardMaster;

import java.util.List;

/**
 * Created by CBS on 03-09-2020.
 */
@Dao
public interface OutwardMasterDao {

    @Query("SELECT slno, ifnull(PalletCode,'') PalletCode, ifnull(PalletLocationCode, '') PalletLocationCode, ChBox FROM OutwardMaster where Flag = '0'")
    LiveData<List<OutwardMaster>> getData();

    @Query("SELECT slno, PalletCode, PalletLocationCode FROM OutwardMaster ")
    List<OutwardMaster> postDataToServer();

    @Insert
    void add(OutwardMaster... outwardMasters);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<OutwardMaster> outwardMasters);

    @Query("UPDATE OutwardMaster set PalletCode = :pallet where slno = :id")
    void updatePallet(String pallet, int id);

    @Query("UPDATE OutwardMaster set PalletLocationCode = :palletLocation where slno = :id")
    void updatePalletLocation(String palletLocation, int id);

    @Query("UPDATE OutwardMaster set PalletLocationCode = :palletLocation where PalletLocationCode = ''")
    void updatePalletLocationEmpty(String palletLocation);

    @Query("SELECT COUNT(PalletCode) from OutwardMaster where PalletLocationCode = ''")
    int countPalletLocationEmpty();

    @Query("SELECT COUNT(PalletCode) FROM OutwardMaster")
    LiveData<Integer> getRowCount();

    @Query("UPDATE OutwardMaster set Flag = '1' where Flag = '0'")
    void updateFlagDraft();

    @Query("UPDATE OutwardMaster set Flag = '-1' where Flag = '0' AND slno = :id")
    void updateCheckBoxFlag(int id);

    @Query("DELETE FROM OutwardMaster WHERE Flag = '-1'")
    void deleteData();

    @Query("DELETE FROM OutwardMaster WHERE slno = :id")
    void deleteData(Integer id);

    @Query("DELETE FROM OutwardMaster")
    void deleteAllData();
}



