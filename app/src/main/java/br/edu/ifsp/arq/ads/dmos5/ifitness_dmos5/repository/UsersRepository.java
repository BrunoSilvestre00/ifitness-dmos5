package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.database.AppDatabase;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.database.UserDAO;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.ActivityHistory;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.User;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.UserHasActivity;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.viewmodel.UserViewModel;

//import br.edu.ifsp.arq.dmos5.lojavirtualatletica.database.AppDatabase;
//import br.edu.ifsp.arq.dmos5.lojavirtualatletica.database.UsuarioDao;
//import br.edu.ifsp.arq.dmos5.lojavirtualatletica.model.Endereco;
//import br.edu.ifsp.arq.dmos5.lojavirtualatletica.model.Usuario;
//import br.edu.ifsp.arq.dmos5.lojavirtualatletica.model.UsuarioComEndereco;
//import br.edu.ifsp.arq.dmos5.lojavirtualatletica.viewmodel.UsuarioViewModel;

public class UsersRepository {

    private static final String BASE_URL = "https://identitytoolkit.googleapis.com/v1/";
    private static final String SIGNUP = "accounts:signUp";
    private static final String SIGNIN = "accounts:signInWithPassword";
    private static final String PASSWORD_RESET = "accounts:sendOobCode";
    private static final String KEY = "?key=AIzaSyBJi-fFwm0EicBZjV7ODh9dO_sfRZ9dUzI";

    private UserDAO dao;

    private FirebaseFirestore firestore;

    private RequestQueue queue;

    private SharedPreferences preference;

    public UsersRepository(Application application) {
        dao = AppDatabase.getInstance(application).dao();
        firestore = FirebaseFirestore.getInstance();
        queue = Volley.newRequestQueue(application);
        preference = PreferenceManager.getDefaultSharedPreferences(application);
    }

    public void createUser(User user){
        JSONObject parametros = new JSONObject();
        try{
            parametros.put("email", user.getEmail());
            parametros.put("password", user.getPassword());
            parametros.put("returnSecureToken", true);
            parametros.put("Content-Type",
                    "application/json; charset=utf-8");
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + SIGNUP + KEY,
                parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            user.setId(response.getString("localId"));
                            user.setPassword(response.getString("idToken"));

                            firestore.collection("user")
                                    .document(user.getId()).set(user)
                                    .addOnSuccessListener(unused -> {
                                        Log.d(this.toString(), "Usuário " +
                                                user.getEmail() + "cadastrado com sucesso.");
                                    });
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Deu pau esta merda", error.getMessage());
                    }
                }
        );
        queue.add(request);
    }

    public LiveData<User> login(String email, String senha){
        MutableLiveData<User> liveData = new MutableLiveData<>();

        JSONObject parametros = new JSONObject();
        try {
            parametros.put("email", email);
            parametros.put("password", senha);
            parametros.put("returnSecureToken", true);
            parametros.put("Content-Type", "application/json; charset=utf-8");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + SIGNIN + KEY, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String localId = response.getString("localId");
                            String idToken = response.getString("idToken");

                            firestore.collection("user").document(localId).get().addOnSuccessListener(snapshot -> {
                                User user = snapshot.toObject(User.class);

                                user.setId(localId);
                                user.setPassword(idToken);

                                liveData.setValue(user);

                                preference.edit().putString(UserViewModel.USER_ID, localId).apply();

                                String sid = preference.getString(UserViewModel.USER_ID, "kekw");
                                Log.d("SID", "from login "+sid);

                                firestore.collection("user").document(localId).set(user);
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                                Log.d(this.toString(), obj.toString());
                            } catch ( UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                        liveData.setValue(null);
                    }
                });

        queue.add(request);

        return liveData;
    }


    public LiveData<UserHasActivity> load(String userID){
        UserHasActivity userHasActivity = new UserHasActivity();
        MutableLiveData<UserHasActivity> liveData = new MutableLiveData<>();

        DocumentReference userRef = firestore.collection("user").document(userID);

        userRef.get().addOnSuccessListener(snapshot -> {
            User user = snapshot.toObject(User.class);

            userHasActivity.setUser(user);

            userRef.collection("activity_history").get().addOnCompleteListener( snap -> {
                snap.getResult().forEach(doc -> {
                    ActivityHistory activityHistory = doc.toObject(ActivityHistory.class);
                    activityHistory.setId(doc.getId());
                    userHasActivity.getActivitys().add(activityHistory);
                });

                liveData.setValue(userHasActivity);
                Log.d("SID", "from load inside ref "+liveData.getValue().getUser().getName());
            });
        });

        return liveData;
    }

    public LiveData<UserHasActivity> loadUsersActivitys(String userID){
        return dao.loadUsersActivitys(userID);
    }

    public Boolean update(UserHasActivity userHasActivity){
        final Boolean[] atualizado = {false};

        DocumentReference userRef = firestore.collection("user").document(userHasActivity.getUser().getId());

        userRef.set(userHasActivity.getUser()).addOnSuccessListener(unused -> {
            atualizado[0] = true;
        });

        CollectionReference activityRef = userRef.collection("activity_history");

        ActivityHistory activityHistory = userHasActivity.getActivitys().get(0);

        if(activityHistory.getId().isEmpty()){
            activityRef.add(activityHistory).addOnSuccessListener( end ->{
                activityHistory.setId(end.getId());
                atualizado[0] = true;
            });
        }else{
            activityRef.document(activityHistory.getId()).set(activityHistory).addOnSuccessListener(unused -> {
                atualizado[0] = true;
            });
        }

        return atualizado[0];
    }

    public void resetPassword(String email){
        JSONObject parametros = new JSONObject();
        try {
            parametros.put("email", email);
            parametros.put("requestType", "PASSWORD_RESET");
            parametros.put("Content-Type", "application/json; charset=utf-8");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + PASSWORD_RESET + KEY, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(this.toString(), response.keys().toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                                Log.d(this.toString(), obj.toString());
                            } catch ( UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                });

        queue.add(request);
    }
}










