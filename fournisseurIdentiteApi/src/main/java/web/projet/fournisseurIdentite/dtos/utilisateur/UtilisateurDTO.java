package web.projet.fournisseurIdentite.dtos.utilisateur;

import java.time.LocalDate;

import lombok.Data;
import web.projet.fournisseurIdentite.dtos.sexe.SexeDTO;
import web.projet.fournisseurIdentite.models.Sexe;

@Data
public class UtilisateurDTO {
    private Long id;

    private String email;
    private String nom;
    private String prenom;
    private LocalDate date_naissance;
    private String mot_de_passe;
    private Boolean etat = false;  
    private Integer nb_tentative = 0;
    private SexeDt sexe;
}
