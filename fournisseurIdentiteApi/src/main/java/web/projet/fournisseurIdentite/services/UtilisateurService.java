package web.projet.fournisseurIdentite.services;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurLoginDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurResponseDTO;
import web.projet.fournisseurIdentite.mappers.UtilisateurMapper;
import web.projet.fournisseurIdentite.models.Utilisateur;
import web.projet.fournisseurIdentite.repositories.ConfigurationRepository;
import web.projet.fournisseurIdentite.repositories.UtilisateurRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

@Service
@Transactional
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private UtilisateurMapper utilisateurMapper;
    @Autowired
    private ConfigurationRepository configurationRepository;
    

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
    
     public UtilisateurResponseDTO authenticate(UtilisateurLoginDTO loginDTO) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Récupérer la limite des tentatives à partir de la table 'configurations'
        int maxAttempts = getMaxAttempts();

        // Si le nombre de tentatives dépasse la limite, bloquer l'utilisateur
        if (utilisateur.getNb_tentative() >= maxAttempts) {
            throw new RuntimeException("Trop de tentatives. Compte bloqué temporairement.");
        }

        if (!BCrypt.checkpw(loginDTO.getMotDePasse(), utilisateur.getMot_de_passe())) {
            incrementNbTentative(utilisateur);
            throw new RuntimeException("Email ou mot de passe incorrect.");
        }

        // Réinitialiser le nombre de tentatives en cas de succès
        resetNbTentative(utilisateur);
        return utilisateurMapper.toResponseDTO(utilisateur);
    }
    
    private int getMaxAttempts() {
        return configurationRepository.findByCle("nbtentative")
                .map(config -> Integer.parseInt(config.getValeur()))
                .orElseThrow(() -> new RuntimeException("Clé 'nbtentative' introuvable dans la table configurations"));
    }

    private void incrementNbTentative(Utilisateur utilisateur) {
        utilisateur.setNb_tentative(utilisateur.getNb_tentative() + 1);
        utilisateurRepository.save(utilisateur);
    }

    private void resetNbTentative(Utilisateur utilisateur) {
        utilisateur.setNb_tentative(0);
        utilisateurRepository.save(utilisateur);
    }
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

}
