package com.android.testmessenger.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.android.testmessenger.dao.VerificationDao;
import com.android.testmessenger.model.Verification;


@Database( entities = {Verification.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context){
        if(appDatabase == null){
            String DB_NAME = "DomainManager";
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    public abstract VerificationDao verificationDao();

    public static void destroyInstance() {
        appDatabase = null;
    }
}
