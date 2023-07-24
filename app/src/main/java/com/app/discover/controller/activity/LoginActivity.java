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

public class LoginActivity extends AppCompatActivity {


    private Button btnLogin, btnRegister ;
    private TextInputEditText txEmail, txPassword;
    private Context context;

    private String email="", password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInputDataEmpty(txEmail) || isInputDataEmpty(txPassword)){
                    Snackbar.make(view, "Veuillez remplir tout les champ avant de continuer !", Snackbar.LENGTH_SHORT).show();
                }
                else{
                    startNewActivity(MainActivity.class);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity(RegistrationActivity.class);
            }
        });

    }

    private void initialize(){
        btnLogin = findViewById(R.id.log_login_button);
        btnRegister = findViewById(R.id.log_register_button);
        txEmail = findViewById(R.id.log_textfield_email);
        txPassword = findViewById(R.id.log_textfield_password);
        context = this;
    }
    private Boolean isInputDataEmpty(TextInputEditText textInputEditText){

        String inputText = textInputEditText.getText().toString().trim();

        if (inputText.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }
    private void startNewActivity(Class activity){
        Intent intent = new Intent(context, activity);
        startActivity(intent);
        finish();
    }

    private void getInputData(){
        email = String.valueOf(txEmail.getText());
        password = String.valueOf(txPassword.getText());
    }
}