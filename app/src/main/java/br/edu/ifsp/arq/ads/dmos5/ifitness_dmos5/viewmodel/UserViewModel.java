package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Optional;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.User;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.UserHasActivity;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.repository.UsersRepository;

//import br.edu.ifsp.arq.dmos5.lojavirtualatletica.model.Endereco;
//import br.edu.ifsp.arq.dmos5.lojavirtualatletica.model.Usuario;
//import br.edu.ifsp.arq.dmos5.lojavirtualatletica.model.UsuarioComEndereco;
//import br.edu.ifsp.arq.dmos5.lojavirtualatletica.repository.UsuariosRepository;

public class UserViewModel extends AndroidViewModel {

    public static final String USUARIO_ID = "USUARIO_ID";

    private UsersRepository usersRepository;


    public UserViewModel(@NonNull Application application) {
        super(application);
        usersRepository = new UsersRepository(application);
    }

    public void createUser(User user){
        usersRepository.createUsuario(user);
    }

    public void update(UserHasActivity usuarioComEndereco){
        usersRepository.update(usuarioComEndereco);
    }

    public LiveData<User> login(String email, String password) {
        return usersRepository.login(email, password);
    }

    public void logout(){
        PreferenceManager.getDefaultSharedPreferences(getApplication())
                .edit().remove(USUARIO_ID)
                .apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<UserHasActivity> isLogged(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        Optional<String> id = Optional.ofNullable(sharedPreferences.getString(USUARIO_ID, null));
        if(!id.isPresent()){
            return new MutableLiveData<>(null);
        }
        return usersRepository.load(id.get());
    }

    public void resetPassword(String email) {
        usersRepository.resetPassword(email);
    }
}






