package web.projet.fournisseurIdentite.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.projet.fournisseurIdentite.dtos.sexe.SexeDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurCreateDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurUpdateDTO;
import web.projet.fournisseurIdentite.models.Sexe;
import web.projet.fournisseurIdentite.models.Utilisateur;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-18T19:48:19+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.5 (Ubuntu)"
)
@Component
public class UtilisateurMapperImpl implements UtilisateurMapper {

    @Override
    public Utilisateur toUtilisateur(UtilisateurDTO utilisateurDTO) {
        if ( utilisateurDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setId( utilisateurDTO.getId() );
        utilisateur.setEmail( utilisateurDTO.getEmail() );
        utilisateur.setNom( utilisateurDTO.getNom() );
        utilisateur.setPrenom( utilisateurDTO.getPrenom() );
        utilisateur.setDate_naissance( utilisateurDTO.getDate_naissance() );
        utilisateur.setMot_de_passe( utilisateurDTO.getMot_de_passe() );
        utilisateur.setEtat( utilisateurDTO.getEtat() );
        utilisateur.setNb_tentative( utilisateurDTO.getNb_tentative() );
        utilisateur.setSexe( sexeDTOToSexe( utilisateurDTO.getSexe() ) );

        return utilisateur;
    }

    @Override
    public Utilisateur toUtilisateur(UtilisateurCreateDTO utilisateurCreateDTO) {
        if ( utilisateurCreateDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setEmail( utilisateurCreateDTO.getEmail() );
        utilisateur.setNom( utilisateurCreateDTO.getNom() );
        utilisateur.setPrenom( utilisateurCreateDTO.getPrenom() );
        utilisateur.setDate_naissance( utilisateurCreateDTO.getDate_naissance() );
        utilisateur.setMot_de_passe( utilisateurCreateDTO.getMot_de_passe() );

        return utilisateur;
    }

    @Override
    public Utilisateur toUtilisateur(UtilisateurUpdateDTO utilisateurCreateDTO) {
        if ( utilisateurCreateDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setNom( utilisateurCreateDTO.getNom() );
        utilisateur.setPrenom( utilisateurCreateDTO.getPrenom() );
        utilisateur.setDate_naissance( utilisateurCreateDTO.getDate_naissance() );
        utilisateur.setMot_de_passe( utilisateurCreateDTO.getMot_de_passe() );

        return utilisateur;
    }

    @Override
    public UtilisateurDTO toUtilisateurDTO(Utilisateur utilisateur) {
        if ( utilisateur == null ) {
            return null;
        }

        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();

        utilisateurDTO.setId( utilisateur.getId() );
        utilisateurDTO.setEmail( utilisateur.getEmail() );
        utilisateurDTO.setNom( utilisateur.getNom() );
        utilisateurDTO.setPrenom( utilisateur.getPrenom() );
        utilisateurDTO.setDate_naissance( utilisateur.getDate_naissance() );
        utilisateurDTO.setMot_de_passe( utilisateur.getMot_de_passe() );
        utilisateurDTO.setEtat( utilisateur.getEtat() );
        utilisateurDTO.setNb_tentative( utilisateur.getNb_tentative() );
        utilisateurDTO.setSexe( sexeToSexeDTO( utilisateur.getSexe() ) );

        return utilisateurDTO;
    }

    @Override
    public void updateUtilisateurFromDTO(UtilisateurUpdateDTO utilisateurUpdateDTO, Utilisateur utilisateur) {
        if ( utilisateurUpdateDTO == null ) {
            return;
        }

        utilisateur.setNom( utilisateurUpdateDTO.getNom() );
        utilisateur.setPrenom( utilisateurUpdateDTO.getPrenom() );
        utilisateur.setDate_naissance( utilisateurUpdateDTO.getDate_naissance() );
        utilisateur.setMot_de_passe( utilisateurUpdateDTO.getMot_de_passe() );
    }

    protected Sexe sexeDTOToSexe(SexeDTO sexeDTO) {
        if ( sexeDTO == null ) {
            return null;
        }

        Sexe sexe = new Sexe();

        if ( sexeDTO.getId() != null ) {
            sexe.setId( sexeDTO.getId().longValue() );
        }
        sexe.setSexe( sexeDTO.getSexe() );

        return sexe;
    }

    protected SexeDTO sexeToSexeDTO(Sexe sexe) {
        if ( sexe == null ) {
            return null;
        }

        SexeDTO sexeDTO = new SexeDTO();

        if ( sexe.getId() != null ) {
            sexeDTO.setId( sexe.getId().intValue() );
        }
        sexeDTO.setSexe( sexe.getSexe() );

        return sexeDTO;
    }
}
