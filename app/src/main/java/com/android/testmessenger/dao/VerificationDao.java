package com.android.testmessenger.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.android.testmessenger.model.Verification;

@Dao
public interface VerificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Verification verification);

    @Query( "Select * From verification where id= :id" )
    Verification getVerificaion(int id);

    @Delete
    void delete(Verification verification);
}
