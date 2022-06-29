package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.ActivityHistory;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.User;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.UserHasActivity;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    User login(String email, String password);

    @Transaction
    @Query("SELECT * FROM user WHERE id = :userID")
    LiveData<UserHasActivity> loadUsersActivitys(String userID);

    @Insert
    void insert(User user);

    @Insert
    void insert(ActivityHistory activityHistory);

    @Update
    void update(User user);

    @Update
    void update(ActivityHistory activityHistory);
}
