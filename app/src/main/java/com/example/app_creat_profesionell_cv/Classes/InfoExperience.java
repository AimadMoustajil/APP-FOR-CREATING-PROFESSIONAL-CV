package com.example.app_creat_profesionell_cv.Classes;

public class InfoExperience {
    String nomEntreprise,titreDePoste,dateDébut,dateDeFin,Résumé;

    public InfoExperience(String nomEntreprise, String titreDePoste, String dateDébut, String dateDeFin, String résumé) {
        this.nomEntreprise = nomEntreprise;
        this.titreDePoste = titreDePoste;
        this.dateDébut = dateDébut;
        this.dateDeFin = dateDeFin;
        Résumé = résumé;
    }

    public InfoExperience() {
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getTitreDePoste() {
        return titreDePoste;
    }

    public void setTitreDePoste(String titreDePoste) {
        this.titreDePoste = titreDePoste;
    }

    public String getDateDébut() {
        return dateDébut;
    }

    public void setDateDébut(String dateDébut) {
        this.dateDébut = dateDébut;
    }

    public String getDateDeFin() {
        return dateDeFin;
    }

    public void setDateDeFin(String dateDeFin) {
        this.dateDeFin = dateDeFin;
    }

    public String getRésumé() {
        return Résumé;
    }

    public void setRésumé(String résumé) {
        Résumé = résumé;
    }
}
