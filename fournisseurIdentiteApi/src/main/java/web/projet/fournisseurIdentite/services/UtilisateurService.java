package web.projet.fournisseurIdentite.services;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurCreateDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurUpdateDTO;
import web.projet.fournisseurIdentite.mappers.UtilisateurMapper;
import web.projet.fournisseurIdentite.models.Sexe;
import web.projet.fournisseurIdentite.models.Utilisateur;
import web.projet.fournisseurIdentite.repositories.SexeRepository;
import web.projet.fournisseurIdentite.repositories.UtilisateurRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private SexeRepository sexeRepository;
    @Autowired
    private UtilisateurMapper utilisateurMapper;

    public UtilisateurDTO save(UtilisateurCreateDTO data) {
        Utilisateur utilisateur = utilisateurMapper.toUtilisateur(data);
        Sexe sexe = sexeRepository.findById(data.getId_sexe()).orElseThrow(() -> new RuntimeException("sexe not found"));
        utilisateur.setSexe(sexe);
        utilisateur.setEtat(false);
        // mbola asina an'le nbr tentative
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toUtilisateurDTO(savedUtilisateur);
    }

    public UtilisateurDTO update(Long id, UtilisateurUpdateDTO data) {
        Utilisateur existingUtilisateur = utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur not found with id " + id));
        utilisateurMapper.updateUtilisateurFromDTO(data, existingUtilisateur);
        Sexe sexe = sexeRepository.findById(data.getId_Sexe()).orElseThrow(() -> new RuntimeException("sexe not found"));
        existingUtilisateur.setSexe(sexe);
        Utilisateur savedUtilisateur = utilisateurRepository.save(existingUtilisateur);
        return utilisateurMapper.toUtilisateurDTO(savedUtilisateur);
    }

}
