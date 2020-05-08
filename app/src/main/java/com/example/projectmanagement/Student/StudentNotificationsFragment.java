package com.example.projectmanagement.Student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectmanagement.Class.Notification;
import com.example.projectmanagement.Class.NotificationAdapter;
import com.example.projectmanagement.Class.SessionHandler;
import com.example.projectmanagement.Class.Student;
import com.example.projectmanagement.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentNotificationsFragment extends Fragment {

    private String Host, URL_Notification ;
    List<Notification> NotificationList ;
    RecyclerView recyclerView ;
    private SessionHandler session;
    private Student student;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_student_notifications, container, false);

        Host = getString(R.string.localhost);
        URL_Notification = Host+"/ProjectManagement/StudentNotification.php";
        session = new SessionHandler(getActivity());
        student=session.getStudentDetails();

        recyclerView = (RecyclerView) root.findViewById(R.id.StudentRecyclerView) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        NotificationList = new ArrayList<Notification>() ;
        importNotification();

        return root;
    }

    public void importNotification(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Notification,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONArray jArray = json.getJSONArray("list");
                            Toast.makeText(getActivity(), "Upload ! " + student.getBatch() + student.getGuide(), Toast.LENGTH_SHORT).show();
                            for(int i=0; i<jArray.length(); i++){
                                JSONObject json_data = jArray.getJSONObject(i);
                                Notification notification = new Notification(
                                        json_data.getString("notification"),
                                        json_data.getString("batch"),
                                        json_data.getString("guide"),
                                        json_data.getString("date")
                                        );
                                NotificationList.add(notification);
                            }
                            NotificationAdapter adapter = new NotificationAdapter(getActivity(),NotificationList) ;
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Upload Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Upload -- Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("batch", student.getBatch());
                params.put("guide", student.getGuide());;
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}