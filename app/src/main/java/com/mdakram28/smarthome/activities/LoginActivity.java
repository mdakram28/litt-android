package com.mdakram28.smarthome.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.gson.JsonElement;
import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.listeners.SocketConnectedListener;
import com.mdakram28.smarthome.util.Preferences;
import com.mdakram28.smarthome.retrofit.APIClient;
import com.mdakram28.smarthome.retrofit.AuthAPI;
import com.mdakram28.smarthome.websocket.Socket;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity{

    @BindView(R.id.input_username)
    EditText _usernameText;

    @BindView(R.id.input_password)
    EditText _passwordText;

    @BindView(R.id.btn_login)
    Button _loginButton;

    @BindView(R.id.link_signup)
    TextView _signupLink;

    AuthAPI authAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        authAPI = APIClient.getClient().create(AuthAPI.class);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        if(Preferences.getAccessToken(this) != null){
            onLoginSuccess();
        }
    }

    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.MyMaterialTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        authAPI.login(_usernameText.getText().toString(), _passwordText.getText().toString()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.code() == 200) {
                    Log.d("API", response.body().toString());
                    String token = null;
                    try {
                        token = response.body().getAsJsonObject().get("token").getAsString()    ;
                        Log.d("API", "Token = "+token);
                        Preferences.setAccessToken(getBaseContext(), token);
                        final Socket socket = Socket.getSocket();
                        try {
                            socket.connect(token);
                            socket.addConnectedListener(new SocketConnectedListener() {
                                @Override
                                public void onConnect() {
                                    socket.requestDeviceConfig();
                                    socket.requestRoomConfig();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        onLoginSuccess();
                    } catch (Exception e) {
                        onLoginFailed();
                        e.printStackTrace();
                    }
                }else{
                    onLoginFailed();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                onLoginFailed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _usernameText.setError("enter a valid email address");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 100) {
            _passwordText.setError("between 4 and 20 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
