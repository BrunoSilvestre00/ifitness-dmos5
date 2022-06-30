package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.ActivityHistory;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.Atividades;

public class RankingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Ranking");


        // colocar na tela
        List<ActivityHistory> teste = new ArrayList<>();
        ActivityHistory a = new ActivityHistory("", Atividades.CAMINHADA, 12.9,1.5,"12/09/2021");
        ActivityHistory b = new ActivityHistory("", Atividades.CICLISMO, 12.9,1.5,"12/09/2021");
        ActivityHistory c = new ActivityHistory("", Atividades.CORRIDA, 12.9,1.5,"12/09/2021");

        teste.add(a);
        teste.add(b);
        teste.add(c);




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}