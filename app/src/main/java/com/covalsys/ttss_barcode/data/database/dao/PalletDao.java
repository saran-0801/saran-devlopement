package com.covalsys.ttss_barcode.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.ttss_barcode.data.database.entities.InwardMaster;
import com.covalsys.ttss_barcode.data.database.entities.Pallet;

import java.util.List;

/**
 * Created by CBS on 03-09-2020.
 */
@Dao
public interface PalletDao {

    @Query("SELECT * FROM Pallet")
    LiveData<List<Pallet>> getData();

    @Insert
    void add(Pallet... pallets);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<Pallet> pallets);

    @Query("SELECT COUNT(PalletCode) FROM Pallet")
    LiveData<Integer> getRowCount();

    @Query("SELECT COUNT(PalletCode) FROM Pallet")
    int getCount();

    @Query("SELECT COUNT(PalletCode) from InwardMaster where slno = (SELECT max(slno) FROM Pallet)")
    int tableExistNo();

    @Query("SELECT slno from InwardMaster where slno = (SELECT max(slno) FROM Pallet)")
    int updatedLocationValue();

    @Query("SELECT a.slno, a.PalletCode, b.PalletLocationCode FROM Pallet a inner join PalletLocation b on a.slno = b.slno")
    LiveData<List<InwardMaster>> getPostData();

    @Query("SELECT a.slno, a.PalletCode, b.PalletLocationCode FROM Pallet a inner join PalletLocation b on a.slno = b.slno")
    List<InwardMaster> postDataToServer();

    @Query("SELECT EXISTS (SELECT 1 FROM Pallet WHERE PalletCode =:id)")
    int isAddToCart(String id);

    @Query("UPDATE Pallet set Flag = '1' where Flag = '0'")
    void updateFlagDraft();

    @Query("UPDATE Pallet set Flag = '-1' where Flag = '0' AND slno = :id")
    void updateCheckBoxFlag(int id);

    /*@Query("DELETE FROM Pallet WHERE ChBox = 'True'")
    void deleteSelectedAllData();*/

    @Query("DELETE FROM Pallet WHERE slno = :id")
    void deleteData(Integer id);

    @Query("DELETE FROM Pallet")
    void deleteAllData();
}



