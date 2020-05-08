package com.example.projectmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etConfirmPassword, etFullName, specification, batch, etGuide ;
    private Button student, guide, register, login ;
    private String username, password, confirmPassword, fullName, specificationStr, batchStr, etGuideStr, role ;
    private ProgressDialog pDialog;
    private String Host, register_url ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFullName = findViewById(R.id.etFullName);
        specification = findViewById(R.id.etSpecification) ;
        batch = findViewById(R.id.etBatch) ;
        etGuide = findViewById(R.id.etGuideName) ;
        register = findViewById(R.id.btnRegister);
        login = findViewById(R.id.btnRegisterLogin);
        student = findViewById(R.id.student) ;
        guide = findViewById(R.id.guide) ;
        Host = this.getString(R.string.localhost) ;
        register_url = Host+"/ProjectManagement/RegisterGuide.php";
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_url = Host+"/ProjectManagement/RegisterStudent.php" ;
                specification.setVisibility(View.VISIBLE);
                batch.setVisibility(view.VISIBLE);
                etGuide.setVisibility(View.VISIBLE);
                student.setBackgroundColor(Color.GREEN);
                guide.setBackgroundColor(Color.GRAY);
                register.setVisibility(View.VISIBLE);
                role = "S" ;
            }
        });

        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_url = Host+"/ProjectManagement/RegisterGuide.php" ;
                specification.setVisibility(View.GONE);
                batch.setVisibility(view.GONE);
                etGuide.setVisibility(View.GONE);
                register.setVisibility(View.VISIBLE);
                guide.setBackgroundColor(Color.GREEN);
                student.setBackgroundColor(Color.GRAY);
                role = "G" ;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                batchStr = batch.getText().toString().trim();
                specificationStr = specification.getText().toString().trim();
                etGuideStr = etGuide.getText().toString().trim();
                fullName = etFullName.getText().toString().trim();
                if (validateInputs()) {
                    register.setVisibility(View.GONE);
                    RegisterUser();
                }
            }
        });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(SignupActivity.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private boolean validateInputs() {
        if (fullName.isEmpty()) {
            etFullName.setError("Full Name cannot be empty");
            etFullName.requestFocus();
            return false;
        }
        if(role.equals("S"))
        {
            if (batchStr.isEmpty()) {
                batch.setError("Batch cannot be empty");
                batch.requestFocus();
                return false;

            }
            if (specificationStr.isEmpty()) {
                specification.setError("Specification cannot be empty");
                specification.requestFocus();
                return false;

            }
            if (etGuideStr.isEmpty()) {
                etGuide.setError("Guide cannot be empty");
                etGuide.requestFocus();
                return false;

            }
        }
        if (username.isEmpty()) {
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }

        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError("Confirm Password cannot be empty");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password and Confirm Password does not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void RegisterUser() {

        displayLoader();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, register_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            Toast.makeText(SignupActivity.this, "Response! \n" + message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignupActivity.this, "Register Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        register.setVisibility(View.VISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignupActivity.this, "Register -- Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                        register.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("full_name", fullName);
                if(role.equals("S"))
                {
                    params.put("specification",specificationStr) ;
                    params.put("batch",batchStr) ;
                    params.put("guide",etGuideStr) ;
                }
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
        requestQueue.add(stringRequest);

    }
}