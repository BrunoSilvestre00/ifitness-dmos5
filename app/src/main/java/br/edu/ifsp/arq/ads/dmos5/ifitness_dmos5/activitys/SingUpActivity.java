package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.User;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.viewmodel.UserViewModel;

public class SingUpActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TextView txtTitulo;

    private TextInputEditText txtName, txtDate;
    private TextInputEditText txtEmail, txtPassword;

    private Spinner spnGender;
    private Button btnSignUp;

    private UserViewModel userViewModel;

    private boolean validate() {
        boolean isValid = true;
        if (txtName.getText().toString().trim().isEmpty()) {
            txtName.setError("Preencha o campo nome");
            isValid = false;
        } else {
            txtName.setError(null);
        }

        if (txtEmail.getText().toString().trim().isEmpty()) {
            txtEmail.setError("Preencha o campo e-mail");
            isValid = false;
        } else {
            txtEmail.setError(null);
        }

        if (txtPassword.getText().toString().trim().isEmpty()) {
            txtPassword.setError("Preencha o campo senha");
            isValid = false;
        } else {
            if(txtPassword.getText().toString().length() < 8){
                txtPassword.setError("A senha deve conter ao menos 8 caracteres");
                isValid = false;
            }else {
                txtPassword.setError(null);
            }
        }

        return isValid;
    }

    private void signUp(){
        if(validate()) {
            User user = new User(
                    txtName.getText().toString().trim(),
                    txtEmail.getText().toString().trim(),
                    txtPassword.getText().toString().trim(),
                    txtDate.getText().toString().trim(),
                    getResources().getStringArray(R.array.genders)[spnGender.getSelectedItemPosition()],
                    "",
                    ""
            );

            userViewModel.createUser(user);

            Toast.makeText(
                    SingUpActivity.this,
                    String.format("Usuário %s criado com sucesso!", user.getEmail()),
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void createComponents(){
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        toolbar = findViewById(R.id.toolbar);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Cadastro");

        txtName = findViewById(R.id.txt_edit_nome);
        txtDate = findViewById(R.id.txt_edit_data);
        txtEmail = findViewById(R.id.txt_edit_email);
        txtPassword = findViewById(R.id.txt_edit_cadastrar_senha);

        spnGender = findViewById(R.id.sp_gender);

        btnSignUp = findViewById(R.id.btn_usuario_cadastrar);
        btnSignUp.setOnClickListener(view -> signUp());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        createComponents();

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