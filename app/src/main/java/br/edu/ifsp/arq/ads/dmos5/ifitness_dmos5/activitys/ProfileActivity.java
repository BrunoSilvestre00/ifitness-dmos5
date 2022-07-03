package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.User;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.UserHasActivity;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.viewmodel.UserViewModel;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private UserViewModel userViewModel;
    private TextInputEditText txtEmail, txtName, txtDataNascimento, txtTelefone;
    private Spinner spnGender;
    private Button btnUpdate;



    private void createComponents(){
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        toolbar = findViewById(R.id.toolbar);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Perfil");

        txtName = findViewById(R.id.txt_editprofile_nome);
        txtEmail = findViewById(R.id.txt_editprofile_email);
        spnGender = findViewById(R.id.sp_gender);
        txtDataNascimento = findViewById(R.id.txt_edit_data);
        txtTelefone = findViewById(R.id.txt_edit_phone);

        btnUpdate = findViewById(R.id.btn_usuario_atualizar);
        btnUpdate.setOnClickListener(view -> updateProfile());

    }

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

        if (txtDataNascimento.getText().toString().trim().isEmpty()) {
            txtDataNascimento.setError("Preencha o campo Data de nascimento");
            isValid = false;
        }
        else {
            txtDataNascimento.setError(null);
        }

        if (txtTelefone.getText().toString().trim().isEmpty()) {
            txtTelefone.setError("Preencha o campo telefone");
            isValid = false;
        } else {
                txtTelefone.setError(null);
        }

        return isValid;
    }

    private void updateProfile(){

        if(!validate()){
            return;
        }

        User user = new User(txtName.getText().toString(),
                txtEmail.getText().toString(),
               "",
                txtDataNascimento.getText().toString(),
                getResources().getStringArray(R.array.genders)[spnGender.getSelectedItemPosition()],
                txtTelefone.getText().toString(),
                ""
        );
        userViewModel.isLogged().observe(this,  new Observer<UserHasActivity>(){

            @Override
            public void onChanged(UserHasActivity userHasActivity) {
               user.setId(userHasActivity.getUser().getId());
               user.setPassword(userHasActivity.getUser().getPassword());
               userHasActivity.setUser(user);
               userViewModel.update(userHasActivity);
               Toast.makeText(ProfileActivity.this, "Dados Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
               finish();

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setSupportActionBar(toolbar);

        createComponents();

        userViewModel.isLogged().observe(this,  new Observer<UserHasActivity>(){

            @Override
            public void onChanged(UserHasActivity userHasActivity) {
                if(userHasActivity!=null){
                    txtName.setText(userHasActivity.getUser().getName());
                    txtEmail.setText(userHasActivity.getUser().getEmail());
                    String[] sexo = getResources().getStringArray(R.array.genders);
                    for (int i = 0; i < sexo.length; i++){
                        if(sexo[i].equals(userHasActivity.getUser().getGender())){
                            spnGender.setSelection(i);
                        }
                    }
                    txtDataNascimento.setText(userHasActivity.getUser().getBornDate());
                    txtTelefone.setText(userHasActivity.getUser().getPhone());
                }
                else{

                    startActivity(new Intent(ProfileActivity.this,
                            SingUpActivity.class));
                    finish();

                }


            }
        });


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