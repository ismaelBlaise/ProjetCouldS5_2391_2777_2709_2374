package web.projet.fournisseurIdentite.dtos.utilisateur;

import java.time.LocalDate;

import lombok.Data;
import web.projet.fournisseurIdentite.dtos.sexe.SexeDTO;

@Data
public class UtilisateurDTO {
    private Integer id;
    private String email;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String passe;
    private SexeDTO sexe;
}
