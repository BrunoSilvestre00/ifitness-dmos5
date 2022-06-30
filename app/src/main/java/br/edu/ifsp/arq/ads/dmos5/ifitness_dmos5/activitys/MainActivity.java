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
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

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
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        break;
                    case R.id.nav_account:
                        intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_activity:
                        // colocar aqui
                        break;
                    case R.id.nav_statitics:
                        // colocar validações aqui
                        break;
                    case R.id.nav_ranking:
                        intent = new Intent(MainActivity.this, RankingActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        //colocar validações aqui
                        finish();

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
                        Toast.makeText(MainActivity.this, usersActivitys.getUser().getName(), Toast.LENGTH_LONG).show();
                        ActivityHistory activityHistory = new ActivityHistory(
                                usersActivitys.getUser().getId(),
                                Atividades.CAMINHADA,
                                150,
                                30,
                                "27/06/2022"
                        );
                        usersActivitys.getActivitys().add(activityHistory);
                        userViewModel.update(usersActivitys);
                    }
                });
            }
        });


        /*User user = new User(
                "Rodolffo Rodrigues",
                "ro_ro@email.com",
                "987654321",
                "17/07/1998",
                "male",
                "(16) 99845-6512",
                "",
                2, 2, 1, 4
        );

        userViewModel.createUser(user);*/

        userViewModel.logout();

        //userViewModel.login("ro_ro@email.com", "987654321")
        userViewModel.login("mary_silva@email.com", "123456")
                .observe(MainActivity.this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if(user == null){
                            Toast.makeText(getApplicationContext(), "NÃO LOGOU",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), user.getName() + " está Logado nessa porra",
                                    Toast.LENGTH_SHORT).show();
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