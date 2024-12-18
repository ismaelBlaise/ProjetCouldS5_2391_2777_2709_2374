package web.projet.fournisseurIdentite.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.projet.fournisseurIdentite.models.Token;
import web.projet.fournisseurIdentite.repositories.TokenRepository;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
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
        LocalDateTime tokenExpirationTime = token.getDate_expiraton();

        // Vérifier si le token est expiré
        if (tokenExpirationTime.isBefore(LocalDateTime.now())) {
            return false; // Le token est expiré
        }

        return true; // Le token est encore valide
    }
}

