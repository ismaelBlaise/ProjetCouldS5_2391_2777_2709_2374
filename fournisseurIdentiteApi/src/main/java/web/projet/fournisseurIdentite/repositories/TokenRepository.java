package web.projet.fournisseurIdentite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import web.projet.fournisseurIdentite.models.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}