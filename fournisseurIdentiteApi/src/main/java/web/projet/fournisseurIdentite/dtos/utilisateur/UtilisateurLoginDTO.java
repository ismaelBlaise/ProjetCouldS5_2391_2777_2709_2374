package web.projet.fournisseurIdentite.dtos.utilisateur;

public class UtilisateurLoginDTO {
    private String email;
    private String motDePasse;

    // Constructeurs

    public UtilisateurLoginDTO() {
    }

    public UtilisateurLoginDTO(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
    }

    // Getters et Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
