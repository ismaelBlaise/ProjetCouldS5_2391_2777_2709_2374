package web.projet.fournisseurIdentite.dtos.utilisateur;

import java.time.LocalDate;

import lombok.Data;
import web.projet.fournisseurIdentite.dtos.sexe.SexeDTO;

@Data
public class UtilisateurUpdateDTO {
    private String nom;
    private String prenom;
    private LocalDate date_naissance;
    private String mot_de_passe;
    private SexeDTO sexe;
}
