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
import com.app.discover.controller.DataManager;
import com.app.discover.dal.interfaces.UserInterface;
import com.app.discover.dal.service.UserService;
import com.app.discover.model.Information;
import com.app.discover.model.Setting;
import com.app.discover.model.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister ;
    private TextInputEditText txFullname, txEmail, txPassword, txPasswordConfirmation;
    private Context context;
    private UserService userService;
    private User user;
    private Gson gson;
    private JSONObject jsonObject;
    private String url;
    private String password="", passwordConfirmation="";
    private Information response;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();
        addAllValueToObject();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInputDataEmpty(txFullname) || isInputDataEmpty(txEmail) || isInputDataEmpty(txPassword) || isInputDataEmpty(txPasswordConfirmation)){
                    Snackbar.make(view, "Veuillez remplir tout les champ avant de continuer !", Snackbar.LENGTH_LONG).show();
                }
                else{
                    if(password.equals(passwordConfirmation)){
                        sendData(view);
                    }
                    else{
                        Snackbar.make(view, "Veuillez saisir un mot de passe identique dans les deux champs !", Snackbar.LENGTH_LONG).show();
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

    private void init(){
        btnRegister = findViewById(R.id.reg_register_button);
        btnLogin = findViewById(R.id.reg_login_button);
        txFullname = findViewById(R.id.reg_textfield_fullname);
        txEmail = findViewById(R.id.reg_textfield_email);
        txPassword = findViewById(R.id.reg_textfield_password);
        txPasswordConfirmation = findViewById(R.id.reg_textfield_password_confirmation);
        context = this;
        userService = new UserService(context);
        user = new User();
        gson = new Gson();
        jsonObject = null;
        response = null;
        url = "http://192.168.56.1:8000/users";
        dataManager = DataManager.getInstance(context);
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

    private void sendData(View view) {

        try {
            jsonObject = new JSONObject(gson.toJson(user));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        userService.addAndGetUser(url, jsonObject, new UserInterface() {
            @Override
            public void handleObjectResponse(JSONObject jsonObject) {
                response = gson.fromJson(jsonObject.toString(), Information.class);
                Setting setting = dataManager.getSetting();
                setting.setInformation(response);
                dataManager.saveSetting(setting, context);
                startNewActivity(MainActivity.class);
            }

            @Override
            public void handleArrayResponse(JSONArray jsonArray) {

            }

            @Override
            public void handleError(VolleyError volleyError) {

            }

        });

    }

    private void addAllValueToObject(){
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

        txFullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                user.setFullName(String.valueOf(txFullname.getText()));
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
                password = String.valueOf(txPassword.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txPasswordConfirmation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordConfirmation = String.valueOf(txPasswordConfirmation.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

}