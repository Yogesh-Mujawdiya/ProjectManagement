package com.example.projectmanagement.Class;

public class Notification {
    String Description , Batch , Guide_Name, Date ;

    public Notification() { }

    public Notification(String description, String batch, String guide_Name, String date) {
        Description = description;
        Batch = batch;
        Guide_Name = guide_Name;
        Date = date;
    }

    public String getDescription() { return Description; }

    public void setDescription(String description) { Description = description; }

    public String getBatch() { return Batch; }

    public void setBatch(String batch) { Batch = batch; }

    public String getGuide_Name() { return Guide_Name; }

    public void setGuide_Name(String guide_Name) { Guide_Name = guide_Name; }

    public String getDate() { return Date; }

    public void setDate(String date) { Date = date; }
}
