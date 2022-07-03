package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.ActivityHistory;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.Atividades;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.User;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.UserHasActivity;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.viewmodel.UserViewModel;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView txtTitulo;
    private TextView txtLogin;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.nav_drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.toogle_open,
                R.string.toogle_close);

        drawerLayout.addDrawerListener(toggle);


        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        break;
                    case R.id.nav_account:
                        intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_activity:
                        userViewModel.isLogged().observe(MainActivity.this, new Observer<UserHasActivity>() {
                            @Override
                            public void onChanged(UserHasActivity usersActivitys) {
                                if(usersActivitys != null) {
                                    Intent intent = new Intent(MainActivity.this, SportActivity.class);
                                    intent.putExtra("user", usersActivitys);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(
                                            MainActivity.this,
                                            "Você precisa estar logado\npara acessar as suas Atividades",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                        });
                        break;
                    case R.id.nav_statitics:
                        userViewModel.isLogged().observe(MainActivity.this, new Observer<UserHasActivity>() {
                            @Override
                            public void onChanged(UserHasActivity usersActivitys) {
                                if(usersActivitys != null) {
                                    Intent intent = new Intent(MainActivity.this, StatisticActivity.class);
                                    intent.putExtra("user", usersActivitys);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(
                                            MainActivity.this,
                                            "Você precisa estar logado\npara acessar as Estatísticas",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                        });
                        break;
                    case R.id.nav_ranking:
                        intent = new Intent(MainActivity.this, RankingActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        userViewModel.logout();
                        txtLogin.setText(R.string.enter);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }
        });

        txtLogin = navigationView.getHeaderView(0)
                .findViewById(R.id.header_profile_name);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userViewModel.isLogged().observe(MainActivity.this, new Observer<UserHasActivity>() {
                    @Override
                    public void onChanged(UserHasActivity usersActivitys) {
                        if(usersActivitys == null) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(
                                    MainActivity.this,
                                    String.format("%s já está logado!", usersActivitys.getUser().getName()),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userViewModel.isLogged().observe(this, new Observer<UserHasActivity>() {
            @Override
            public void onChanged(UserHasActivity usersActivitys) {
                if(usersActivitys != null){
                    txtLogin.setText(usersActivitys.getUser().getName());
                    /*String perfilImage = PreferenceManager
                            .getDefaultSharedPreferences(MainActivity.this)
                            .getString(MediaStore.EXTRA_OUTPUT, null);
                    if(perfilImage != null){
                        imagePerfil.setImageURI(Uri.parse(perfilImage));
                    }else{
                        imagePerfil.setImageResource(R.drawable.profile_image);
                    }*/
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}