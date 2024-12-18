package web.projet.fournisseurIdentite.services;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurUpdateDTO;
import web.projet.fournisseurIdentite.mail.*;
import web.projet.fournisseurIdentite.mappers.UtilisateurMapper;
import web.projet.fournisseurIdentite.models.Token;
import web.projet.fournisseurIdentite.models.Utilisateur;
import web.projet.fournisseurIdentite.models.Configuration;
import web.projet.fournisseurIdentite.models.Sexe;
import web.projet.fournisseurIdentite.repositories.SexeRepository;
import web.projet.fournisseurIdentite.repositories.TokenRepository;
import web.projet.fournisseurIdentite.repositories.UtilisateurRepository;
import web.projet.fournisseurIdentite.repositories.ConfigurationRepository;

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
    private ConfigurationRepository configurationRepository;

    public UtilisateurDTO save(UtilisateurDTO data) {
        Utilisateur utilisateur = utilisateurMapper.toUtilisateur(data);
        return utilisateurMapper.toUtilisateurDTO(utilisateur);
    }

    public UtilisateurDTO update(Long id, UtilisateurUpdateDTO data) {
        Utilisateur existingUtilisateur = utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur not found with id " + id));
        utilisateurMapper.updateUtilisateurFromDTO(data, existingUtilisateur);
        Sexe sexe = sexeRepository.findById(data.getId_Sexe()).orElseThrow(() -> new RuntimeException("sexe not found"));
        existingUtilisateur.setSexe(sexe);
        Utilisateur savedUtilisateur = utilisateurRepository.save(existingUtilisateur);
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

    public String  validationPin(ValidationPinDTO validationPinDTO) {
        String email = validationPinDTO.getEmail();
        int codePin = validationPinDTO.getCodepin();
        
        // Vérifier si l'utilisateur existe
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));
    
        // Vérifier si le nombre de tentatives est épuisé
        if (utilisateur.getNb_tentative() == 0 ) {
            UtilisateurDTO utilisateurdto=UtilisateurMapper.toUtilisateurDTO(utilisateur);
            demanderReinitialisation(utilisateurDTO);
            // throw new RuntimeException("Trop de tentatives. Compte bloqué temporairement.");
        }
    
        // Vérifier si le Code PIN est valide
        CodePin codePinEntity = codePinRepository.findByCodepin(codePin)
                .orElse(null);
    
        if (codePinEntity != null && !codePinEntity.getDateExpiration().isBefore(LocalDateTime.now())) {
            resetNbTentative(utilisateur);
            Token token=tokenservice.creationToken(utilisateur);
            token=tokenservice.genererDateExpiration(token);
            tokenRepository.save(token);
            return token.getToken();
        } 
        // Si le Code PIN est incorrect ou expiré
        incrementNbTentative(utilisateur);
        System.out.println("Code PIN valide pour l'utilisateur : " + utilisateur.getEmail());
        return null;
        // throw new RuntimeException("Code PIN incorrect ou expiré.");
    

        
        
    }
    


    private int getMaxAttempts() {
        Configuration conf=configurationRepository.findByCle("nbtentative").get();
        int value=Integer.parseInt(conf.getValeur());
        return value;
    }

    private void incrementNbTentative(Utilisateur utilisateur) {
        utilisateur.setNb_tentative(utilisateur.getNb_tentative() - 1);
        System.out.println("Utilisateur defaut : " + (utilisateur.getNb_tentative() - 1));
        utilisateurRepository.save(utilisateur);
    }

    private void resetNbTentative(Utilisateur utilisateur) {
        utilisateur.setNb_tentative(getMaxAttempts());
        utilisateurRepository.save(utilisateur);
    }      
}
