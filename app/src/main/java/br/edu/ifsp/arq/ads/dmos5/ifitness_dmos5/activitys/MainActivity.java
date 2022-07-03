package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.ActivityHistory;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.Atividades;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.UserHasActivity;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView txtTitulo;
    private TextView txtLogin;

    private TextView lblActivityType, lblActivityPts, lblActivityLevel;

    private ListView lastActivitysList;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Home");

        lblActivityType = findViewById(R.id.lbl_activity_type);
        lblActivityPts = findViewById(R.id.lbl_activity_pts);
        lblActivityLevel = findViewById(R.id.lbl_activity_level);

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
                        lastActivitysList = (ListView) findViewById(R.id.last_activity_list);
                        ArrayAdapter<ActivityHistory> adapter = new ArrayAdapter<ActivityHistory>(
                                MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                new ArrayList<ActivityHistory>()
                        );
                        lastActivitysList.setAdapter(adapter);

                        lblActivityType.setText("");
                        lblActivityPts.setText("");
                        lblActivityLevel.setText("");
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
                    lastActivitysList = (ListView) findViewById(R.id.last_activity_list);
                    ArrayAdapter<ActivityHistory> adapter = new ArrayAdapter<ActivityHistory>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            usersActivitys.getActivitys().subList(0,5)
                    );
                    lastActivitysList.setAdapter(adapter);

                    Atividades better = usersActivitys.getBetterActivity();

                    lblActivityType.setText(better.toString());
                    lblActivityPts.setText(String.format("%.0f pts", usersActivitys.getDistTotalActivity(better)));
                    lblActivityLevel.setText(String.format("Nível %d", usersActivitys.getLevelActivity(better)));
                } else{
                    lastActivitysList = (ListView) findViewById(R.id.last_activity_list);
                    ArrayAdapter<ActivityHistory> adapter = new ArrayAdapter<ActivityHistory>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            new ArrayList<ActivityHistory>()
                    );
                    lastActivitysList.setAdapter(adapter);

                    lblActivityType.setText("");
                    lblActivityPts.setText("");
                    lblActivityLevel.setText("");
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