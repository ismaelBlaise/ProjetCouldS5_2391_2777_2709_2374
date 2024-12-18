package web.projet.fournisseurIdentite.repositories;

import java.lang.module.Configuration;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
    Optional<Configuration> findByCle(String cle);
}

