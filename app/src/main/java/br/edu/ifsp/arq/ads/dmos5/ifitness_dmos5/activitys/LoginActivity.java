package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.User;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private TextInputEditText txtEmail, txtPassword;

    private Button btnLogin, btnSignUp;

    private UserViewModel userViewModel;

    private void signIn(){
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        //userViewModel.login("bruno.silvestre@aluno.ifsp.edu.br", "12345678")
        userViewModel.login(email, password)
            .observe(LoginActivity.this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if(user == null){
                        Toast.makeText(getApplicationContext(), "Conflito nas credenciais",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        finish();
                    }
                }
            });
    }

    private void signUp(){
        Intent intent = new Intent(LoginActivity.this, SingUpActivity.class);
        startActivity(intent);
    }
    
    private void createComponents(){
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        toolbar = findViewById(R.id.toolbar);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Login");

        txtEmail = findViewById(R.id.txt_edit_login_email);
        txtPassword = findViewById(R.id .txt_edit_login_senha);

        btnLogin = findViewById(R.id.btn_usuario_entrar);
        btnLogin.setOnClickListener(view -> signIn());

        btnSignUp = findViewById(R.id.btn_login_cadastrar);
        btnSignUp.setOnClickListener(view -> signUp());
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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