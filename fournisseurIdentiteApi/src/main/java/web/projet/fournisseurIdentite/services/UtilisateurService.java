package web.projet.fournisseurIdentite.services;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.mail.*;
// import web.projet.fournisseurIdentite.mail.EmailService;
import web.projet.fournisseurIdentite.mappers.UtilisateurMapper;
import web.projet.fournisseurIdentite.models.Token;
import web.projet.fournisseurIdentite.models.Utilisateur;
import web.projet.fournisseurIdentite.repositories.TokenRepository;
import web.projet.fournisseurIdentite.repositories.UtilisateurRepository;

import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;

import java.time.LocalDateTime;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
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
    private TokenRepository tokenRepository;
    @Autowired
    private TokenService tokenService;

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


    public String inscrireUtilisateur(UtilisateurDTO dto) throws Exception{
        Utilisateur utilisateur= utilisateurRepository.findByEmail(dto.getEmail()).get();
        if(utilisateur!=null && utilisateur.getEtat()==false){
            
            
            
        }
        else if(utilisateur!=null && utilisateur.getEtat()==true){
            throw new RuntimeException("L'adresse email est deja utilise");
        }

        utilisateur= utilisateurMapper.toUtilisateur(dto);
        utilisateur.setMot_de_passe(BCrypt.hashpw(dto.getMot_de_passe(), BCrypt.gensalt(10)));
        utilisateur.setEtat(false);
        utilisateurRepository.save(utilisateur);

       
        Token token=tokenRepository.save(tokenService.creationToken(utilisateur));

        String tokenStr=creationUrlValidation(token);

        emailValidation(dto, tokenStr);
        
       
        return tokenStr;
    }

    

    

    public String creationUrlValidation(Token token){
    
        String validationUrl = "http://localhost:8080/api/utilisateurs/valider?token=" + token.getToken();
        return validationUrl;
    }


    public void emailValidation(UtilisateurDTO dto,String validationUrl) throws Exception{
        EmailConfig config = new EmailConfig("smtp.gmail.com", 587, "rarianamiadana@gmail.com", "mgxypljhfsktzlbk");
        String destinataires = dto.getEmail();

        String sujet = "Confirmation Email";
    
        String contenuHTML = "Cliquer sur cette url pour valider votre compte : "+validationUrl;
        
    
        EmailService emailService = new EmailService(config);
        emailService.sendEmail(destinataires, sujet, contenuHTML);  
    }

    public void validerCompte(String tokenStr) {
        Token token = tokenRepository.findByToken(tokenStr)
                .orElseThrow(() -> new RuntimeException("Token invalide ou expiré"));

        Utilisateur utilisateur = token.getUtilisateur();
        utilisateur.setEtat(true);
        utilisateurRepository.save(utilisateur);
        tokenRepository.delete(token);
    }
}
