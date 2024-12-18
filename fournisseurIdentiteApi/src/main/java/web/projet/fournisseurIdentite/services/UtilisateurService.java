package web.projet.fournisseurIdentite.services;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.mail.*;
import web.projet.fournisseurIdentite.mappers.SexeMapper;
// import web.projet.fournisseurIdentite.mail.EmailService;
import web.projet.fournisseurIdentite.mappers.UtilisateurMapper;
import web.projet.fournisseurIdentite.models.Token;
import web.projet.fournisseurIdentite.models.Utilisateur;
import web.projet.fournisseurIdentite.models.Configuration;
import web.projet.fournisseurIdentite.models.Sexe;
import web.projet.fournisseurIdentite.repositories.SexeRepository;
import web.projet.fournisseurIdentite.repositories.TokenRepository;
import web.projet.fournisseurIdentite.repositories.UtilisateurRepository;
import web.projet.fournisseurIdentite.repositories.ConfigurationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private UtilisateurMapper utilisateurMapper;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SexeRepository sexeRepository;
    @Autowired
    private SexeMapper sexeMapper;
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

    public String demanderReinitialisation(UtilisateurDTO dto) throws Exception {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("utilisateur untrouvable"));
        Token newToken = tokenRepository.save(tokenService.creationToken(utilisateur));
        String newUrl = creationUrlValidation(newToken);
        emailReinitialisation(dto, newUrl);
        return newUrl;
    }

    public String inscrireUtilisateur(UtilisateurDTO dto) throws Exception {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByEmail(dto.getEmail());
    
        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
    
            if (utilisateur.getEtat() == false) {
                Token token = tokenService.recupererTokenUtiliateur(dto);
    
                if (token.getDate_expiration().isBefore(LocalDateTime.now())) {
                    Token newToken = tokenRepository.save(tokenService.creationToken(utilisateur));
                    String newUrl = creationUrlValidation(newToken);
                    emailValidation(dto, newUrl);
                    return newUrl;
                }
    
                String url = creationUrlValidation(token);
                emailValidation(dto, url);
                return url;
            } else if (utilisateur.getEtat() == true) {
                throw new RuntimeException("L'adresse email est deja utilise");
            }
        }
    
        // Si aucun utilisateur n'existe avec cet email, en créer un nouveau
        
        dto.setMot_de_passe(BCrypt.hashpw(dto.getMot_de_passe(), BCrypt.gensalt(10)));
        dto.setEtat(false);
        Utilisateur utilisateur=utilisateurMapper.toUtilisateur(save(dto));
        utilisateur.setId(null);
        // System.out.println(utilisateur.toString());
        // Sexe sexe=sexeMapper.toSexe(dto.getSexe());
        // if(!sexeRepository.findById(sexe.getId()).isEmpty()){
        //     sexeRepository.save(sexe);
        // }
        utilisateur=utilisateurRepository.save(utilisateur);
        Token token = tokenRepository.save(tokenService.creationToken(utilisateur));
        String url = creationUrlValidation(token);
        emailValidation(dto, url);
    
        return url;
    }
    

    public String creationUrlValidation(Token token){
    
        String validationUrl = "http://localhost:8080/utilisateurs/valider-compte?token=" + token.getToken();
        return validationUrl;
    }

    public String creationUrlReinitialisation(Token token){
    
        String validationUrl = "http://localhost:8080/utilisateurs/reinitialiser-tentative?token=" + token.getToken();
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

    public void emailReinitialisation(UtilisateurDTO dto,String validationUrl) throws Exception{
        EmailConfig config = new EmailConfig("smtp.gmail.com", 587, "rarianamiadana@gmail.com", "mgxypljhfsktzlbk");
        String destinataires = dto.getEmail();

        String sujet = "Reinitialiser tentative Email";
    
        String contenuHTML = "Cliquer sur cette url pour reinitialiser vos tentative: "+validationUrl;
        
    
        EmailService emailService = new EmailService(config);
        emailService.sendEmail(destinataires, sujet, contenuHTML);  
    }


    public void validerCompte(String tokenStr) {

        Token token = tokenRepository.findByToken(tokenStr)
                .orElseThrow(() -> new RuntimeException("Token invalide ou expiré"));

        if(token.getDate_expiration().isBefore(LocalDateTime.now())){
            throw  new RuntimeException("Token invalide ou expiré");
        }   
        Utilisateur utilisateur = token.getUtilisateur();
        utilisateur.setEtat(true);
        Configuration configuration=configurationRepository.findByCle("nbtentative").get();
        utilisateur.setNb_tentative(Integer.parseInt(configuration.getValeur()));
        utilisateurRepository.save(utilisateur);
        tokenRepository.delete(token);
    }

    public void reinitialiserTentative(String tokenStr) {
        Token token = tokenRepository.findByToken(tokenStr).orElseThrow(() -> new RuntimeException("Token invalide ou expiré"));
        Utilisateur utilisateur = token.getUtilisateur();
        Configuration configuration=configurationRepository.findByCle("nbtentative").get();
        utilisateur.setNb_tentative(Integer.parseInt(configuration.getValeur()));
        utilisateurRepository.save(utilisateur);
        tokenRepository.delete(token);
    }


}
