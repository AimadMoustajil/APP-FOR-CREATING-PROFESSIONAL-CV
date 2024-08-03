package com.example.app_creat_profesionell_cv.Classes;

import java.util.ArrayList;

public class InfoAdditionnelle {
    ArrayList<Competance> competanceArrayList;
    ArrayList<SoftSkills> softSkillsArrayList;
    ArrayList<Langue> langueArrayList;
    ArrayList<Loisir> loisirArrayList;
    String Linkdin;
    String Github;
    String LetteCode;

    public InfoAdditionnelle(ArrayList<Competance> competanceArrayList, ArrayList<SoftSkills> softSkillsArrayList, ArrayList<Langue> langueArrayList, ArrayList<Loisir> loisirArrayList, String linkdin, String github, String letteCode) {
        this.competanceArrayList = competanceArrayList;
        this.softSkillsArrayList = softSkillsArrayList;
        this.langueArrayList = langueArrayList;
        this.loisirArrayList = loisirArrayList;
        Linkdin = linkdin;
        Github = github;
        LetteCode = letteCode;
    }

    public InfoAdditionnelle() {
    }

    public ArrayList<Competance> getCompetanceArrayList() {
        return competanceArrayList;
    }

    public void setCompetanceArrayList(ArrayList<Competance> competanceArrayList) {
        this.competanceArrayList = competanceArrayList;
    }

    public ArrayList<SoftSkills> getSoftSkillsArrayList() {
        return softSkillsArrayList;
    }

    public void setSoftSkillsArrayList(ArrayList<SoftSkills> softSkillsArrayList) {
        this.softSkillsArrayList = softSkillsArrayList;
    }

    public ArrayList<Langue> getLangueArrayList() {
        return langueArrayList;
    }

    public void setLangueArrayList(ArrayList<Langue> langueArrayList) {
        this.langueArrayList = langueArrayList;
    }

    public ArrayList<Loisir> getLoisirArrayList() {
        return loisirArrayList;
    }

    public void setLoisirArrayList(ArrayList<Loisir> loisirArrayList) {
        this.loisirArrayList = loisirArrayList;
    }

    public String getLinkdin() {
        return Linkdin;
    }

    public void setLinkdin(String linkdin) {
        Linkdin = linkdin;
    }

    public String getGithub() {
        return Github;
    }

    public void setGithub(String github) {
        Github = github;
    }

    public String getLetteCode() {
        return LetteCode;
    }

    public void setLetteCode(String letteCode) {
        LetteCode = letteCode;
    }
}
