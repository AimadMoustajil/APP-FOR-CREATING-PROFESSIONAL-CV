package com.example.app_creat_profesionell_cv.Classes;

import java.util.ArrayList;

public class InfoAdditionnelle {
    ArrayList<String> competanceArrayList;
    ArrayList<String> softSkillsArrayList;
    ArrayList<String> langueArrayList;
    ArrayList<String> loisirArrayList;
    String linkdin;
    String github;
    String leetCode;

    public InfoAdditionnelle(ArrayList<String> competanceArrayList, ArrayList<String> softSkillsArrayList, ArrayList<String> langueArrayList, ArrayList<String> loisirArrayList, String linkdin, String github, String leetCode) {
        this.competanceArrayList = competanceArrayList;
        this.softSkillsArrayList = softSkillsArrayList;
        this.langueArrayList = langueArrayList;
        this.loisirArrayList = loisirArrayList;
        this.linkdin = linkdin;
        this.github = github;
        this.leetCode = leetCode;
    }

    public InfoAdditionnelle() {
    }

    public ArrayList<String> getCompetanceArrayList() {
        return competanceArrayList;
    }

    public void setCompetanceArrayList(ArrayList<String> competanceArrayList) {
        this.competanceArrayList = competanceArrayList;
    }

    public ArrayList<String> getSoftSkillsArrayList() {
        return softSkillsArrayList;
    }

    public void setSoftSkillsArrayList(ArrayList<String> softSkillsArrayList) {
        this.softSkillsArrayList = softSkillsArrayList;
    }

    public ArrayList<String> getLangueArrayList() {
        return langueArrayList;
    }

    public void setLangueArrayList(ArrayList<String> langueArrayList) {
        this.langueArrayList = langueArrayList;
    }

    public ArrayList<String> getLoisirArrayList() {
        return loisirArrayList;
    }

    public void setLoisirArrayList(ArrayList<String> loisirArrayList) {
        this.loisirArrayList = loisirArrayList;
    }

    public String getLinkdin() {
        return linkdin;
    }

    public void setLinkdin(String linkdin) {
        this.linkdin = linkdin;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getLeetCode() {
        return leetCode;
    }

    public void setLeetCode(String leetCode) {
        this.leetCode = leetCode;
    }
}
