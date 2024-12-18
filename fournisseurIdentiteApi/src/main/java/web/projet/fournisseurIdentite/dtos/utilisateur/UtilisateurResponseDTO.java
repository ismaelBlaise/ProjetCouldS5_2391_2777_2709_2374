package web.projet.fournisseurIdentite.dtos.utilisateur;

import java.time.LocalDate;

public class UtilisateurResponseDTO {
    private int idUtilisateur;
    private String email;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private Boolean etat;
    private Integer nbTentative;
    private String sexe;

    // Constructeurs

    public UtilisateurResponseDTO() {
    }

    public UtilisateurResponseDTO(int idUtilisateur, String email, String nom, String prenom,
                                   LocalDate dateNaissance, Boolean etat, Integer nbTentative, String sexe) {
        this.idUtilisateur = idUtilisateur;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.etat = etat;
        this.nbTentative = nbTentative;
        this.sexe = sexe;
    }

    // Getters et Setters

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Integer getNbTentative() {
        return nbTentative;
    }

    public void setNbTentative(Integer nbTentative) {
        this.nbTentative = nbTentative;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
}
