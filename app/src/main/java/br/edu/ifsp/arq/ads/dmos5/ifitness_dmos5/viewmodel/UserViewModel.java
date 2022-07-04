package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Optional;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys.MainActivity;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.ActivityHistory;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.User;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.UserHasActivity;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.repository.UsersRepository;

public class UserViewModel extends AndroidViewModel {

    public static final String USER_ID = "USER_ID";

    private UsersRepository usersRepository;

    private SharedPreferences preference;


    public UserViewModel(@NonNull Application application) {
        super(application);
        usersRepository = new UsersRepository(application);
        preference = PreferenceManager.getDefaultSharedPreferences(application);
    }

    public void createUser(User user){
        usersRepository.createUser(user);
    }

    public void update(UserHasActivity usersActivitys){
        usersRepository.update(usersActivitys);
    }

    public void deleteActivity(ActivityHistory activityHistory){
        usersRepository.deleteActivity(activityHistory);
    }

    public LiveData<User> login(String email, String password) {
        return usersRepository.login(email, password);
    }

    public void logout(){
        preference.edit().remove(USER_ID).apply();
    }

    public LiveData<UserHasActivity> isLogged(){
        String sid = preference.getString(UserViewModel.USER_ID, null);
        if(sid == null){
            return new MutableLiveData<>(null);
        }
        Log.d("SID", "from isLogged "+ sid);
        return usersRepository.load(sid);
    }

    public LiveData<List<UserHasActivity>> getAllUsers(){
        return usersRepository.getAllUsers();
    }

    public void resetPassword(String email) {
        usersRepository.resetPassword(email);
    }
}






