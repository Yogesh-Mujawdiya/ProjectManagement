package com.example.projectmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectmanagement.Class.SessionHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private String username, password;
    private ProgressDialog pDialog;
    private String Host , login_url ;
    private SessionHandler session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        if(session.isLoggedIn()){
            if(session.getRole().equals("S")){
                Intent i = new Intent(getApplicationContext(), StudentActivity.class);
                startActivity(i);
                finish();
            }
            else {
                Intent i = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(i);
                finish();
            }
        }
        setContentView(R.layout.activity_login);
        Host = getString(R.string.localhost);
        login_url =  Host+"/ProjectManagement/login.php";
        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        Button register = findViewById(R.id.btnLoginRegister);
        Button login = findViewById(R.id.btnLogin);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();
                if (validateInputs()) {
                    LoginUser();
                }
            }
        });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private boolean validateInputs() {
        if(username.isEmpty()){
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    public void LoginUser() {
        displayLoader();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            if (success.equals("True")) {
                                Toast.makeText(LoginActivity.this, message , Toast.LENGTH_SHORT).show();
                                session.setRole(jsonObject.getString("role"));
                                if(jsonObject.getString("role").equals("S")) {
                                    session.loginStudent(jsonObject.getString("username"),
                                                        jsonObject.getString("password"),
                                                        jsonObject.getString("full_name"),
                                                        jsonObject.getString("batch"),
                                                        jsonObject.getString("specification"),
                                                        jsonObject.getString("guide"));
                                    Intent i = new Intent(LoginActivity.this, StudentActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                                else {
                                    session.loginGuide(jsonObject.getString("username"),jsonObject.getString("password")
                                                        ,jsonObject.getString("full_name"));
                                    Intent i = new Intent(LoginActivity.this, GuideActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                            else {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error : " + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}