package com.example.app_creat_profesionell_cv.Classes;

public class InfoPersonnelle {
    String logoOfUser,f_name,l_name,n_phone,email,pays,ville,job,about;

    public InfoPersonnelle(String logoOfUser, String f_name, String l_name, String n_phone, String email, String pays, String ville, String job,String about) {
        this.logoOfUser = logoOfUser;
        this.f_name = f_name;
        this.l_name = l_name;
        this.n_phone = n_phone;
        this.email = email;
        this.pays = pays;
        this.ville = ville;
        this.job = job;
        this.about = about;
    }

    public InfoPersonnelle() {

    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLogoOfUser() {
        return logoOfUser;
    }

    public void setLogoOfUser(String logoOfUser) {
        this.logoOfUser = logoOfUser;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getN_phone() {
        return n_phone;
    }

    public void setN_phone(String n_phone) {
        this.n_phone = n_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
