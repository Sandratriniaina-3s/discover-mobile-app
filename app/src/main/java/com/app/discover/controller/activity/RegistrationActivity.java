package com.app.discover.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.discover.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrationActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister ;
    private TextInputEditText txFullname, txEmail, txPassword, txPasswordConfirmation;
    private Context context;

    private String fullName="", email="", password="", passwordConfirmation="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initialize();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInputDataEmpty(txFullname) || isInputDataEmpty(txEmail) || isInputDataEmpty(txPassword) || isInputDataEmpty(txPasswordConfirmation)){
                    Snackbar.make(view, "Veuillez remplir tout les champ avant de continuer !", Snackbar.LENGTH_SHORT).show();
                }
                else{
                    getInputData();
                    if(password.equals(passwordConfirmation)){
                        startNewActivity(MainActivity.class);
                    }
                    else{
                        Snackbar.make(view, "Veuillez saisir un mot de passe identique dans les deux champs !", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity(LoginActivity.class);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startNewActivity(LoginActivity.class);
    }

    private void initialize(){
        btnRegister = findViewById(R.id.reg_register_button);
        btnLogin = findViewById(R.id.reg_login_button);
        txFullname = findViewById(R.id.reg_textfield_fullname);
        txEmail = findViewById(R.id.reg_textfield_email);
        txPassword = findViewById(R.id.reg_textfield_password);
        txPasswordConfirmation = findViewById(R.id.reg_textfield_password_confirmation);
        context = this;
    }

    private void startNewActivity(Class activity){
        Intent intent = new Intent(context, activity);
        startActivity(intent);
        finish();
    }

    private Boolean isInputDataEmpty(TextInputEditText textInputEditText){

        String inputText = textInputEditText.getText().toString().trim();

        if (inputText.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    private void getInputData(){
        fullName = String.valueOf(txFullname.getText());
        email = String.valueOf(txEmail.getText());
        password = String.valueOf(txPassword.getText());
        passwordConfirmation = String.valueOf(txPasswordConfirmation.getText());
    }

}