package com.example.projectmanagement.Student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectmanagement.Class.SessionHandler;
import com.example.projectmanagement.Class.Student;
import com.example.projectmanagement.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class StudentUploadFragment extends Fragment {
    private String Host, URL_UPLOAD ;
    private SessionHandler session;
    private Student student;
    EditText title, domain ;
    Button upload ;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_student_upload, container, false);
        session = new SessionHandler(getActivity());
        student=session.getStudentDetails();
        title = root.findViewById(R.id.edtProjectTitle) ;
        domain = root.findViewById(R.id.edtDomain) ;
        upload = root.findViewById(R.id.btnUpload) ;
        Host = getString(R.string.localhost);
        URL_UPLOAD = Host+"/ProjectManagement/project.php";
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData() ;
            }
        });
        return root;
    }

    private void uploadData() {
        final String titleString = title.getText().toString() ;
        final String domainString =  domain.getText().toString()  ;
        if(titleString.isEmpty())
            title.setError("Please Insert title");
        else if(domainString.isEmpty())
            domain.setError("Please insert domain");
        else
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("status");
                                String message = jsonObject.getString("message");
                                if (success.equals("True")) {
                                    Toast.makeText(getActivity(), "Upload Success!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Upload Not Success! \n" + message, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Upload Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                                upload.setVisibility(View.VISIBLE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Upload -- Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                            upload.setVisibility(View.VISIBLE);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("title", titleString);
                    params.put("domain", domainString);
                    params.put("guide", student.getGuide());
                    params.put("username", student.getUsername());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }
}