package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.ActivityHistory;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.User;

@Database(entities = {User.class, ActivityHistory.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "ifitness.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(Context context){
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigrationFrom(1)
                .build();
    }

    public abstract UserDAO dao();
}







