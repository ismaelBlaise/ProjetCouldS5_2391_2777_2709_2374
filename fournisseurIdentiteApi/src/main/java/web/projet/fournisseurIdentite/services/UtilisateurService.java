package web.projet.fournisseurIdentite.services;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.ValidationPinDTO;
import web.projet.fournisseurIdentite.mappers.UtilisateurMapper;
import web.projet.fournisseurIdentite.models.CodePin;
import web.projet.fournisseurIdentite.models.Configuration;
import web.projet.fournisseurIdentite.models.Utilisateur;
import web.projet.fournisseurIdentite.repositories.CodePinRepository;
import web.projet.fournisseurIdentite.repositories.ConfigurationRepository;
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
    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private CodePinRepository codePinRepository;

    

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

    public void validationPin(ValidationPinDTO validationPinDTO) {
        String email = validationPinDTO.getEmail();
        int codePin = validationPinDTO.getCodepin();

        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));
        CodePin codePinEntity = codePinRepository.findByCodepin(codePin)
                .orElseThrow(() -> {
                    incrementNbTentative(utilisateur);
                    return new RuntimeException("Code PIN incorrect.");
                });
        if (codePinEntity.getDateExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Code PIN expiré.");
        }

        if (utilisateur.getNb_tentative() >= 0) {
            throw new RuntimeException("Trop de tentatives. Compte bloqué temporairement.");
        }
        
        resetNbTentative(utilisateur);
    }


    private int getMaxAttempts() {
        Configuration conf=configurationRepository.findByCle("nbtentative").get();
        int value=Integer.parseInt(conf.getValeur());
        return value;
    }

    private void incrementNbTentative(Utilisateur utilisateur) {
        utilisateur.setNb_tentative(utilisateur.getNb_tentative() - 1);
        utilisateurRepository.save(utilisateur);
    }

    private void resetNbTentative(Utilisateur utilisateur) {
        utilisateur.setNb_tentative(getMaxAttempts());
        utilisateurRepository.save(utilisateur);
    }

}
