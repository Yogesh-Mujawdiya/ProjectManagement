package com.example.projectmanagement.Guide;

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
import com.example.projectmanagement.Class.Guide;
import com.example.projectmanagement.Class.SessionHandler;
import com.example.projectmanagement.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class GuideNotificationsFragment extends Fragment {

    private String Host, URL_POST ;
    private SessionHandler session;
    private Guide guide;
    EditText Notification, Batch ;
    Button Post ;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_guide_notifications, container, false);
        session = new SessionHandler(getActivity());
        guide = session.getGuideDetails();
        Notification = root.findViewById(R.id.etNotification) ;
        Batch = root.findViewById(R.id.etBatch) ;
        Post = root.findViewById(R.id.btnPost) ;
        Host = getString(R.string.localhost);
        URL_POST = Host+"/ProjectManagement/PostNotification.php";
        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostNotification() ;
            }
        });
        return root;
    }

    private void PostNotification() {
        final String NotificationString = Notification.getText().toString() ;
        final String BatchString =  Batch.getText().toString()  ;
        if(NotificationString.isEmpty())
            Notification.setError("Please Insert title");
        else if(BatchString.isEmpty())
            Batch.setError("Please insert domain");
        else
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST,
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
                                Post.setVisibility(View.VISIBLE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Upload -- Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                            Post.setVisibility(View.VISIBLE);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Notification", NotificationString);
                    params.put("Batch", BatchString);
                    params.put("Full_Name", guide.getFullName());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }
}