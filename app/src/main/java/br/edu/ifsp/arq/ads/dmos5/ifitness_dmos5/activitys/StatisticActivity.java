package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.Atividades;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.UserHasActivity;

public class StatisticActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private UserHasActivity usersActivitys;

    private ImageView chevronLeft, chevronRight, badge;

    private TextView txtActivityType, txtActivityLevel, txtActivityDistance, txtActivityDuration, txtActivityPoints;

    private final Atividades[] atividades = Atividades.values();
    private int index;

    private void createComponents() {
        usersActivitys = (UserHasActivity) getIntent().getExtras().getSerializable("user");

        toolbar = findViewById(R.id.toolbar);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Estatísticas");

        index = 0;

        chevronLeft = findViewById(R.id.chevron_left);
        chevronLeft.setOnClickListener(v -> {
            index = Math.max(index - 1, 0);
            updateInfo(atividades[(index) % 4]);
        });

        chevronRight = findViewById(R.id.chevron_right);
        chevronRight.setOnClickListener(v -> {
            index = Math.min(index + 1, 3);
            updateInfo(atividades[(index) % 4]);
        });

        badge = findViewById(R.id.badge);

        txtActivityType = findViewById(R.id.activity_type);
        txtActivityLevel = findViewById(R.id.activity_level);
        txtActivityDistance = findViewById(R.id.activity_distance);
        txtActivityDuration = findViewById(R.id.activity_duration);
        txtActivityPoints = findViewById(R.id.activity_points);
    }

    private void updateInfo(Atividades selectedActivity){
        int level = usersActivitys.getLevelActivity(selectedActivity);
        double distance = usersActivitys.getDistTotalActivity(selectedActivity);
        double duration = usersActivitys.getTimeTotalActivity(selectedActivity);
        int points = (int) distance;

        txtActivityType.setText(selectedActivity.getType());
        txtActivityLevel.setText(String.format("Nível: %d", level));
        txtActivityDistance.setText(String.format("Distância Total: %.3f Km", distance));
        txtActivityDuration.setText(String.format("Duração Total: %.1f min", duration));
        txtActivityPoints.setText(String.format("Pontuação: %d pts", points));

        String img = "badge_";
        switch (level){
            case 1:
                img += "initial_";
                break;
            case 2:
                img += "bronze_";
                break;
            case 3:
                img += "silver_";
                break;
            case 4:
                img += "gold_";
                break;
            case 5:
                img += "platinum_";
                break;
            default:
                img += "none_";
                break;
        }

        switch (selectedActivity){
            case CAMINHADA:
                img += "walk";
                break;
            case CORRIDA:
                img += "run";
                break;
            case CICLISMO:
                img += "ciclo";
                break;
            case NATACAO:
                img += "swim";
                break;
        }

        int resourceId = getApplication().getResources().getIdentifier(img, "drawable",
                getApplication().getPackageName());

        badge.setImageResource(resourceId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        createComponents();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        updateInfo(atividades[index]);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}