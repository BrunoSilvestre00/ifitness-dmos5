package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.adapter.ActivityAdapter;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.ActivityHistory;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.UserHasActivity;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.viewmodel.UserViewModel;

public class SportActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;

    private UserHasActivity usersActivitys;

    private FloatingActionButton btnNewActivity;

    private ListView activitysList;
    private ActivityAdapter adapter;

    UserViewModel userViewModel;

    private void createComponents(){
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        usersActivitys = (UserHasActivity) getIntent().getExtras().getSerializable("user");

        toolbar = findViewById(R.id.toolbar);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Atividades");

        btnNewActivity = findViewById(R.id.btn_add_activity);
        btnNewActivity.setOnClickListener(view -> addActivity());

        handleAdapter(usersActivitys.getActivitys());
    }

    public void handleAdapter(List<ActivityHistory> listActivitys){
        adapter = new ActivityAdapter(SportActivity.this, android.R.layout.simple_list_item_1, listActivitys);
        activitysList = (ListView) findViewById(R.id.activity_list);
        activitysList.setAdapter(adapter);
    }

    private void addActivity() {
        Intent intent = new Intent(SportActivity.this, NewSportActivity.class);
        startActivity(intent);
    }

    public void deleteActivity(int pos){
        Toast.makeText(this, String.valueOf(pos), Toast.LENGTH_LONG).show();
        userViewModel.isLogged().observe(this, new Observer<UserHasActivity>() {
            @Override
            public void onChanged(UserHasActivity userHasActivity) {
                userViewModel.deleteActivity(userHasActivity.getActivitys().remove(pos));
                handleAdapter(userHasActivity.getActivitys());
                Toast.makeText(
                        SportActivity.this,
                        "Atividade removida com sucesso!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        createComponents();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userViewModel.isLogged().observe(this, new Observer<UserHasActivity>() {
            @Override
            public void onChanged(UserHasActivity userHasActivity) {
                handleAdapter(userHasActivity.getActivitys());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}