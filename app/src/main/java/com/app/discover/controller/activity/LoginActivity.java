package com.app.discover.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import com.android.volley.VolleyError;
import com.app.discover.R;
import com.app.discover.dal.interfaces.UserInterface;
import com.app.discover.dal.service.UserService;
import com.app.discover.model.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private TextInputEditText txEmail, txPassword;
    private Context context;
    private UserService userService;
    private User user, response;
    private Gson gson;
    private JSONObject jsonObject;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        txEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                user.setEmail(String.valueOf(txEmail.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                user.setPassword(String.valueOf(txPassword.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInputDataEmpty(txEmail) || isInputDataEmpty(txPassword)){
                    Snackbar.make(view, "Veuillez remplir tout les champ avant de continuer !", Snackbar.LENGTH_LONG).show();
                }
                else{
                    sendData(view);
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

    private void init(){
        btnLogin = findViewById(R.id.log_login_button);
        btnRegister = findViewById(R.id.log_register_button);
        txEmail = findViewById(R.id.log_textfield_email);
        txPassword = findViewById(R.id.log_textfield_password);
        context = this;
        userService = new UserService(context);
        user = new User();
        gson = new Gson();
        jsonObject = null;
        response = null;
        url = "http://192.168.56.1:8000/users/login";
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
    private void sendData(View view) {

        try {
            jsonObject = new JSONObject(gson.toJson(user));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        userService.addAndGetUser(url, jsonObject, new UserInterface() {
            @Override
            public void handleObjectResponse(JSONObject jsonObject) {
                response = gson.fromJson(jsonObject.toString(), User.class);
                /* SAVE DATA */
                startNewActivity(MainActivity.class);
            }

            @Override
            public void handleArrayResponse(JSONArray jsonArray) {

            }

            @Override
            public void handleError(VolleyError volleyError) {
                Snackbar.make(view, "Échec d'authentification, vérifiez les informations d'identification !", Snackbar.LENGTH_LONG).show();
            }

        });

    }

}