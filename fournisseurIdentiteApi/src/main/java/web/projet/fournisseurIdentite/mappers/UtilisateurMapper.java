package web.projet.fournisseurIdentite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import web.projet.fournisseurIdentite.dtos.sexe.SexeDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurUpdateDTO;
import web.projet.fournisseurIdentite.models.Utilisateur;

@Mapper(componentModel = "spring" , uses = SexeDTO.class)
public interface UtilisateurMapper {
    
    Utilisateur toUtilisateur(UtilisateurDTO utilisateurDTO);

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "email" , ignore = true)
    @Mapping(target = "etat" , ignore = true)
    @Mapping(target = "nb_tentative" , ignore = true)
    Utilisateur toUtilisateur(UtilisateurUpdateDTO utilisateurUpdateDTO);

    UtilisateurDTO toUtilisateurDTO(Utilisateur utilisateur);

    UtilisateurUpdateDTO toUtilisateurUpdateDTO(Utilisateur utilisateur);

}
