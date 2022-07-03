package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.adapter.ActivityAdapter;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.ActivityHistory;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.UserHasActivity;

public class SportActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;

    private UserHasActivity usersActivitys;

    private FloatingActionButton btnNewActivity;

    private ListView activitysList;
    private ActivityAdapter adapter;

    private void createComponents(){
        usersActivitys = (UserHasActivity) getIntent().getExtras().getSerializable("user");

        toolbar = findViewById(R.id.toolbar);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Atividades");

        adapter = new ActivityAdapter(SportActivity.this, android.R.layout.simple_list_item_1, usersActivitys.getActivitys());
        activitysList = (ListView) findViewById(R.id.activity_list);
        activitysList.setAdapter(adapter);

        btnNewActivity = findViewById(R.id.btn_add_activity);
        btnNewActivity.setOnClickListener(view -> AddActivity());
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

    private void AddActivity() {

        Intent intent = new Intent(SportActivity.this, NewSportActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}