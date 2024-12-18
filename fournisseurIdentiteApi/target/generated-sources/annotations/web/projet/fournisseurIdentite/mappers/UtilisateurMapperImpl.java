package web.projet.fournisseurIdentite.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.projet.fournisseurIdentite.dtos.sexe.SexeDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.models.Sexe;
import web.projet.fournisseurIdentite.models.Utilisateur;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-18T23:42:06+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
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

    protected Sexe sexeDTOToSexe(SexeDTO sexeDTO) {
        if ( sexeDTO == null ) {
            return null;
        }

        Sexe sexe = new Sexe();

        sexe.setId( sexeDTO.getId() );
        sexe.setSexe( sexeDTO.getSexe() );

        return sexe;
    }

    protected SexeDTO sexeToSexeDTO(Sexe sexe) {
        if ( sexe == null ) {
            return null;
        }

        SexeDTO sexeDTO = new SexeDTO();

        sexeDTO.setId( sexe.getId() );
        sexeDTO.setSexe( sexe.getSexe() );

        return sexeDTO;
    }
}
