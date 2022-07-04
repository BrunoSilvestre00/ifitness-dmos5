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
    private ActivityHistory activityHistory;

    private void createComponents(){

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        activityHistory = (ActivityHistory) getIntent()
                .getExtras().getSerializable("activity");

        toolbar = findViewById(R.id.toolbar);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Nova Atividade");


        spnActivity = findViewById(R.id.sp_activity);
        txtData = findViewById(R.id.txt_edit_data_activity);
        txtDuration = findViewById(R.id.txt_edit_duration);
        txtDistance = findViewById(R.id.txt_edit_distance);

        btnSave = findViewById(R.id.btn_atividade_add);
        btnSave.setOnClickListener(view -> updateActivity());

    }

    private void fillFields(){
        if(activityHistory.getUserID().isEmpty())
            return;

        Atividades[] atividades = Atividades.values();
        for (int i = 0; i < atividades.length; i++){
            if(atividades[i].equals(activityHistory.getType())){
                spnActivity.setSelection(i);
            }
        }

        txtData.setText(activityHistory.getDate());
        txtDistance.setText(String.valueOf(activityHistory.getDistance()));
        txtDuration.setText(String.valueOf(activityHistory.getDuration()));
    }

    private void updateActivity() {
        userViewModel.isLogged().observe(this, new Observer<UserHasActivity>() {
            @Override
            public void onChanged(UserHasActivity userHasActivity) {
                boolean add = activityHistory.getUserID().isEmpty();
                if(add)
                    activityHistory = new ActivityHistory(userHasActivity.getUser().getId());

                activityHistory.setType(Atividades.values()[spnActivity.getSelectedItemPosition()]);
                activityHistory.setDate(txtData.getText().toString());
                activityHistory.setDistance(Double.parseDouble(txtDistance.getText().toString()));
                activityHistory.setDuration(Double.parseDouble(txtDuration.getText().toString()));

                if(add)
                    userHasActivity.getActivitys().add(activityHistory);
                else{
                    for(int i = 0; i < userHasActivity.getActivitys().size(); i++){
                        if(userHasActivity.getActivitys().get(i).getId().equals(activityHistory.getId())){
                            userHasActivity.getActivitys().set(i, activityHistory);
                            break;
                        }
                    }
                }

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
        fillFields();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}