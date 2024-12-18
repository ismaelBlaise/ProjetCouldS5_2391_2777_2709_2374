package web.projet.fournisseurIdentite.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.projet.fournisseurIdentite.models.Configuration;
import web.projet.fournisseurIdentite.models.Token;
import web.projet.fournisseurIdentite.models.Utilisateur;
import web.projet.fournisseurIdentite.repositories.ConfigurationRepository;
import web.projet.fournisseurIdentite.repositories.TokenRepository;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;
    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token creationToken(Utilisateur utilisateur){
        String tokenStr=UUID.randomUUID().toString();
        Token token= new Token();
        token.setToken(tokenStr);
        token.setUtilisateur(utilisateur);
        token.setDate_expiration(LocalDateTime.now().plusMinutes(1));
        return token;
    }

    public Token genererDateExpiration(Token token){
        Configuration configuration=configurationRepository.findByCle("token_lifetime").get();
        token.setDate_expiration(LocalDateTime.now().plusSeconds(Integer.parseInt(configuration.getValeur())));
        return token;
    }

    /**
     * Vérifie si un token est expiré ou non
     *
     * @param tokenValue La valeur du token à vérifier
     * @return true si le token est valide, false s'il est expiré
     */
    public boolean isTokenValid(String tokenValue) {
        Token token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Token non trouvé."));

        // Calculer l'heure d'expiration du token
        LocalDateTime tokenExpirationTime = token.getDate_expiration();

        // Vérifier si le token est expiré
        if (tokenExpirationTime.isBefore(LocalDateTime.now())) {
            return false; // Le token est expiré
        }

        return true; // Le token est encore valide
    }
}

