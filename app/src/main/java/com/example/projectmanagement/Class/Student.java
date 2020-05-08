package com.example.projectmanagement.Class;


public class Student {
    String username, password, fullName, batch, specialization , guide;


    public void setBatch(String batch) { this.batch = batch; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) { this.password = password; }

    public void setGuide(String guide) { this.guide = guide; }

    public String getPassword() { return password; }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBatch() { return batch; }

    public String getGuide() { return guide; }

    public String getSpecialization() { return specialization; }

    public void setSpecialization(String specification) { this.specialization = specification; }
}
