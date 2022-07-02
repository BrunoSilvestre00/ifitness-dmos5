package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;

public class SportActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private Button btnNewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Atividades");


        btnNewActivity = findViewById(R.id.btn_adicionar_atividade);
        btnNewActivity.setOnClickListener(view -> AddActivity());

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