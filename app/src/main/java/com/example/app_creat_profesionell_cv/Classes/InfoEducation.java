package com.example.app_creat_profesionell_cv.Classes;

public class InfoEducation {
    String shool;
    String metier;
    String startYier;
    String endYier;

    public InfoEducation(String shool, String metier, String startYier, String endYier) {
        this.shool = shool;
        this.metier = metier;
        this.startYier = startYier;
        this.endYier = endYier;
    }

    public InfoEducation() {
    }

    public String getShool() {
        return shool;
    }

    public void setShool(String shool) {
        this.shool = shool;
    }

    public String getMetier() {
        return metier;
    }

    public void setMetier(String metier) {
        this.metier = metier;
    }

    public String getStartYier() {
        return startYier;
    }

    public void setStartYier(String startYier) {
        this.startYier = startYier;
    }

    public String getEndYier() {
        return endYier;
    }

    public void setEndYier(String endYier) {
        this.endYier = endYier;
    }
}
