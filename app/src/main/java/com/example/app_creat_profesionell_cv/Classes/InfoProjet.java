package com.example.app_creat_profesionell_cv.Classes;

public class InfoProjet {
    String nomEntreprise,titreProjet,dateDebut,dateFin,resume;

    public InfoProjet(String nomEntreprise, String titreProjet, String dateDebut, String dateFin, String resume) {
        this.nomEntreprise = nomEntreprise;
        this.titreProjet = titreProjet;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.resume = resume;
    }

    public InfoProjet() {
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getTitreProjet() {
        return titreProjet;
    }

    public void setTitreProjet(String titreProjet) {
        this.titreProjet = titreProjet;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
