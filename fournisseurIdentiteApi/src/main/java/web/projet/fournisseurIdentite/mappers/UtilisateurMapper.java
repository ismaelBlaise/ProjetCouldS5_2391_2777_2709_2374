package web.projet.fournisseurIdentite.mappers;

import org.mapstruct.Mapper;

import web.projet.fournisseurIdentite.dtos.sexe.SexeDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurResponseDTO;
import web.projet.fournisseurIdentite.models.Utilisateur;

@Mapper(componentModel = "spring" , uses = SexeDTO.class)
public interface UtilisateurMapper {
    
    Utilisateur toUtilisateur(UtilisateurDTO utilisateurDTO);

    UtilisateurDTO toUtilisateurDTO(Utilisateur utilisateur);
    
    UtilisateurResponseDTO toResponseDTO(Utilisateur utilisateur);
}
