package com.app.discover.controller.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.app.discover.R;
import com.app.discover.controller.DataManager;
import com.app.discover.controller.activity.LoginActivity;
import com.app.discover.controller.activity.MainActivity;
import com.app.discover.controller.activity.PasswordActivity;
import com.app.discover.dal.interfaces.UserInterface;
import com.app.discover.dal.service.UserService;
import com.app.discover.model.Information;
import com.app.discover.model.Setting;
import com.app.discover.model.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class SettingFragment extends Fragment {

    private DataManager dataManager;
    private Context context;
    private Gson gson;
    private User user;
    private UserService userService;
    private TextView txFullName, txEmail;
    private String url, userId;
    private Button password, logout;
    private SwitchMaterial switcher;

    private static final int REQUEST_CODE = 1;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        dataManager = DataManager.getInstance(context);
        userId = dataManager.getSetting().getInformation().getUserId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        init(view);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context,
                        com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialCalendar_Fullscreen );
                dialog.setTitle("Deconnexion")
                        .setMessage("Souhaitez-vous vraiment deconnecter ?")
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Setting setting = dataManager.getSetting();
                                Information information = new Information();
                                information.setUserId("");
                                setting.setInformation(information);
                                dataManager.saveSetting(setting,context);
                                Intent intent = new Intent(context, LoginActivity.class);
                                startActivity(intent);
                                ((MainActivity)getActivity()).finish();
                            }
                        });
                dialog.show();

            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PasswordActivity.class);
                intent.putExtra("key", gson.toJson(user));
                startActivityForResult(intent, REQUEST_CODE);
            }

        });

        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Setting setting = dataManager.getSetting();
                setting.setNotificationState(b);
                dataManager.saveSetting(setting, context);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == PasswordActivity.RESULT_OK) {
            if (data != null) {
                String result = data.getStringExtra("result_key");
                user.setPassword(result);
                Snackbar.make(requireView(),"Mot de passe modifié avec succès",Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void init(View view){
        txFullName = view.findViewById(R.id.textview_fullname);
        txEmail = view.findViewById(R.id.textview_email);
        userService = new UserService(context);
        gson = new Gson();
        url = "https://discover-api.onrender.com/users/";
        logout = view.findViewById(R.id.setting_logout);
        password = view.findViewById(R.id.setting_password);
        switcher = view.findViewById(R.id.setting_notification);
        switcher.setChecked(dataManager.getSetting().getNotificationState());
        getUser(url);
    }

    private void getUser(String url){
        userService.getUserById(url + userId, new UserInterface() {
            @Override
            public void handleObjectResponse(JSONObject jsonObject) {
                user = gson.fromJson(jsonObject.toString(), User.class);
                setControlValue();
            }

            @Override
            public void handleArrayResponse(JSONArray jsonArray) {

            }

            @Override
            public void handleError(VolleyError volleyError) {

            }
        });
    }

    private void setControlValue(){
        txFullName.setText(user.getFullName());
        txEmail.setText(user.getEmail());
    }

}