package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import static br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.Atividades.CAMINHADA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.adapter.ActivityAdapter;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.ActivityHistory;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.Atividades;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.User;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.UserHasActivity;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.viewmodel.UserViewModel;

public class RankingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo, txtName1, txtName2, txtName3,txtName4, txtName5;
    private TextView txtPoint1, txtPoint2, txtPoint3, txtPoint4, txtPoint5;

    private String userFirst, userSecond, userThird, userFourth, userFifth;
    private int userFirstPts, userSecondPts, userThirdPts, userFourthPts, userFifthPts;

    private void createComponents(){

        toolbar = findViewById(R.id.toolbar);

        userFirst = getIntent().getExtras().getString("user_1");
        userSecond = getIntent().getExtras().getString("user_2");
        userThird = getIntent().getExtras().getString("user_3");
        userFourth = getIntent().getExtras().getString("user_4");
        userFifth = getIntent().getExtras().getString("user_5");

        userFirstPts = getIntent().getExtras().getInt("pts_1");
        userSecondPts = getIntent().getExtras().getInt("pts_2");
        userThirdPts = getIntent().getExtras().getInt("pts_3");
        userFourthPts = getIntent().getExtras().getInt("pts_4");
        userFifthPts = getIntent().getExtras().getInt("pts_5");

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Ranking");

        txtName1 = findViewById(R.id.txt_name_user_1);
        txtName2 = findViewById(R.id.txt_name_user_2);
        txtName3 = findViewById(R.id.txt_name_user_3);
        txtName4 = findViewById(R.id.txt_name_user_4);
        txtName5= findViewById(R.id.txt_name_user_5);

        txtPoint1= findViewById(R.id.txt_point_user_1);
        txtPoint2= findViewById(R.id.txt_point_user_2);
        txtPoint3= findViewById(R.id.txt_point_user_3);
        txtPoint4= findViewById(R.id.txt_point_user_4);
        txtPoint5= findViewById(R.id.txt_point_user_5);
    }

    private void fillFields(){
        txtName1.setText(userFirst);
        txtName2.setText(userSecond);
        txtName3.setText(userThird);
        txtName4.setText(userFourth);
        txtName5.setText(userFifth);

        txtPoint1.setText(String.valueOf(userFirstPts));
        txtPoint2.setText(String.valueOf(userSecondPts));
        txtPoint3.setText(String.valueOf(userThirdPts));
        txtPoint4.setText(String.valueOf(userFourthPts));
        txtPoint5.setText(String.valueOf(userFifthPts));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        createComponents();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fillFields();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}