package web.projet.fournisseurIdentite.services;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.mappers.UtilisateurMapper;
import web.projet.fournisseurIdentite.models.Utilisateur;
import web.projet.fournisseurIdentite.repositories.UtilisateurRepository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private UtilisateurMapper utilisateurMapper;

    

    public UtilisateurDTO save(UtilisateurDTO data) {
        Utilisateur utilisateur = utilisateurMapper.toUtilisateur(data);
        return utilisateurMapper.toUtilisateurDTO(utilisateur);
    }

    public UtilisateurDTO update(Integer id, UtilisateurDTO data) {
        Utilisateur existingUtilisateur = utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur not found with id " + id));
        Utilisateur updateUtilisateur = utilisateurMapper.toUtilisateur(data);
        updateUtilisateur.setId(existingUtilisateur.getId());
        Utilisateur savedUtilisateur = utilisateurRepository.save(updateUtilisateur);
        return utilisateurMapper.toUtilisateurDTO(savedUtilisateur);
    }


    public String inscrireUtilisateur(UtilisateurDTO dto){
        Utilisateur utilisateur= utilisateurMapper.toUtilisateur(dto);

        utilisateur.setEtat(false);
        utilisateurRepository.save(utilisateur);

        String tokenStr=UUID.randomUUID.toString();
        Token token= new Token();s
        token.setToken(tokenStr);
        token.setUtilisateur(utilisateur);
        token.setDate_expiration(LocalDateTime.now().plusMinutes(5));
        tokenRepository.save(token);

        
    }

}
