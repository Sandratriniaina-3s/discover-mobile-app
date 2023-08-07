package com.app.discover.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PasswordActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextInputEditText txCurrentPassword, txNewPassword, txNewPasswordConfirmation;
    private User user, userFromFragment;
    private Gson gson;
    private Button btnSave;
    private String dataFromFragment, url;
    private UserService userService;
    private Context context;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        init();

        addValueToObject();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isInputDataEmpty(txCurrentPassword) || isInputDataEmpty(txNewPassword) || isInputDataEmpty(txNewPasswordConfirmation)){
                    Snackbar.make(view, "Veuillez remplir tout les champ avant de continuer !", Snackbar.LENGTH_LONG).show();
                }
                else{
                    if(userFromFragment.getPassword().equals(String.valueOf(txCurrentPassword.getText()))){

                        try {
                            jsonObject = new JSONObject(gson.toJson(user));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        if(String.valueOf(txNewPassword.getText()).equals(String.valueOf(txNewPasswordConfirmation.getText()))){
                            userService.updateUser(url + userFromFragment.getId(), jsonObject, new UserInterface() {
                                @Override
                                public void handleObjectResponse(JSONObject jsonObject) {
                                    User user =  gson.fromJson(jsonObject.toString(), User.class);
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("result_key", user.getPassword()); // Replace "result_key" with an appropriate key
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();
                                }

                                @Override
                                public void handleArrayResponse(JSONArray jsonArray) {

                                }

                                @Override
                                public void handleError(VolleyError volleyError) {

                                }
                            });
                        }
                        else{
                            Snackbar.make(view, "Vous devez saisir le même mot de passe deux fois", Snackbar.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Snackbar.make(view, "Entrez un mot de passe valide et réessayez", Snackbar.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

    private void init(){
        context = this;
        toolbar = findViewById(R.id.p_appbar);
        txCurrentPassword = findViewById(R.id.p_current_password);
        txNewPassword = findViewById(R.id.p_new_password);
        txNewPasswordConfirmation = findViewById(R.id.p_new_password_confirmation);
        gson = new Gson();
        user = new User();
        btnSave = findViewById(R.id.button_save_password);
        dataFromFragment = getIntent().getStringExtra("key");
        userFromFragment = gson.fromJson(dataFromFragment, User.class);
        url = "https://discover-api.onrender.com/users/";
        userService = new UserService(context);
        jsonObject = null;
    }

    private void addValueToObject(){
        txNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                user.setPassword(txNewPassword.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private Boolean isInputDataEmpty(TextInputEditText textInputEditText){

        String inputText = textInputEditText.getText().toString().trim();

        if (inputText.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

}