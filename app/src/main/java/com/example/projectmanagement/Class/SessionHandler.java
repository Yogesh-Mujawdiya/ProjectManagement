package com.example.projectmanagement.Class;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }


    public void loginStudent(String username, String password, String fullName, String Batch, String Specialization, String guide) {
        mEditor.putString("username", username);
        mEditor.putString("password", password);
        mEditor.putString("full_name", fullName);
        mEditor.putString("batch", Batch);
        mEditor.putString("specialization", Specialization);
        mEditor.putString("guide", guide);
        mEditor.commit();
    }

    public void loginGuide(String username, String password, String fullName) {
        mEditor.putString("username", username);
        mEditor.putString("password", password);
        mEditor.putString("full_name", fullName);
        mEditor.commit();
    }

    public void setRole(String Role){
        mEditor.putString("Role", Role);
        mEditor.commit();
    }

    public String getRole(){
        return mPreferences.getString("Role", "");
    }

    public boolean isLoggedIn() {
        if(mPreferences.contains(KEY_USERNAME)) {
            return true;
        }
        return false;
    }

    public Student getStudentDetails() {

        if (!isLoggedIn()) {
            return null;
        }
        Student S = new Student();
        S.setUsername(mPreferences.getString("username", ""));
        S.setPassword(mPreferences.getString("password", ""));
        S.setFullName(mPreferences.getString("full_name", ""));
        S.setBatch(mPreferences.getString("batch", ""));
        S.setSpecialization(mPreferences.getString("specialization", ""));
        S.setGuide(mPreferences.getString("guide", ""));
        return S;
    }
    public Guide getGuideDetails() {
        if (!isLoggedIn()) {
            return null;
        }
        Guide G = new Guide();
        G.setUsername(mPreferences.getString("username", ""));
        G.setPassword(mPreferences.getString("password", ""));
        G.setFullName(mPreferences.getString("full_name", ""));
        return G;
    }

    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }

}