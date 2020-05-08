package com.example.projectmanagement.Class;

public class Project {

    String Title, Domain, Sid, Gid ;

    public Project(){ }

    public Project(String title, String domain, String sid, String gid) {
        Title = title;
        Domain = domain;
        Sid = sid;
        Gid = gid;
    }

    public String getTitle() { return Title; }

    public void setTitle(String title) { Title = title; }

    public String getDomain() { return Domain; }

    public void setDomain(String domain) { Domain = domain; }

    public String getSid() { return Sid; }

    public void setSid(String sid) { Sid = sid; }

    public String getGid() { return Gid; }

    public void setGid(String gid) { Gid = gid; }
}