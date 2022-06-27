package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.User;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

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
                        //validações aqui
                        break;
                    case R.id.nav_account:
                        //intent = new Intent(MainActivity.this, .class);
                        //startActivity(intent);
                        break;
                    case R.id.nav_activity:
                        // colocar aqui
                        break;
                    case R.id.nav_statitics:
                        // colocar validações aqui
                        break;
                    case R.id.nav_ranking:
                        //colocar validações aqui
                        break;
                    case R.id.nav_logout:
                        //colocar validações aqui
                        finish();

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }
        });

        User user = new User("Bruno", "bruno@email.com", "123456", new Date().toString(), "male", "(xx) 9xxxx-xxxx", "", 1,2,3,4);

        userViewModel.createUser(user);
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