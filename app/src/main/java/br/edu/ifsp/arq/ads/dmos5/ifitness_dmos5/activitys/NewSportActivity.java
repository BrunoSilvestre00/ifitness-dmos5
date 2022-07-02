package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.ActivityHistory;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.Atividades;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.UserHasActivity;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.viewmodel.UserViewModel;

public class NewSportActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private TextInputEditText txtData, txtDuration, txtDistance;
    private Spinner spnActivity;
    private Button btnSave;
    private UserViewModel userViewModel;


    private void createComponents(){

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        toolbar = findViewById(R.id.toolbar);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Nova Atividade");


        spnActivity = findViewById(R.id.sp_activity);
        txtData = findViewById(R.id.txt_edit_data_activity);
        txtDuration = findViewById(R.id.txt_edit_duration);
        txtDistance = findViewById(R.id.txt_edit_distance);

        btnSave = findViewById(R.id.btn_atividade_add);
        btnSave.setOnClickListener(view -> UpdateActivity());

    }

    private void UpdateActivity() {

        ActivityHistory activityHistory = new ActivityHistory(
                "",
               Atividades.values()[spnActivity.getSelectedItemPosition()],
                Double.parseDouble(txtDistance.getText().toString()),
                Double.parseDouble(txtDuration.getText().toString()),
                txtData.getText().toString()

                );

        userViewModel.isLogged().observe(this, new Observer<UserHasActivity>() {
            @Override
            public void onChanged(UserHasActivity userHasActivity) {

                activityHistory.setUserID(userHasActivity.getUser().getId());
                userHasActivity.getActivitys().add(activityHistory);
                userViewModel.update(userHasActivity);
                Toast.makeText(NewSportActivity.this, "Atividade adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sport);

        createComponents();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}