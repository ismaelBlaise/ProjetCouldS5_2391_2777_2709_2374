package web.projet.fournisseurIdentite.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import web.projet.fournisseurIdentite.models.Utilisateur;

import web.projet.fournisseurIdentite.models.Token;

@Service
public class TokenService{
    public Token creationToken(Utilisateur utilisateur){
        String tokenStr=UUID.randomUUID().toString();
        Token token= new Token();
        token.setToken(tokenStr);
        token.setUtilisateur(utilisateur);
        token.setDate_expiration(LocalDateTime.now().plusMinutes(5));
        return token;
    }

    public Token recupererTokenUtiliateur(UtilisateurDTO utilisateurDTO ){
        
    }
}