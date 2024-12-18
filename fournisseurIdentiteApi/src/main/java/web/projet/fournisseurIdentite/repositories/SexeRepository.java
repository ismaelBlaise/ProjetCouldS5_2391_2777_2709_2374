package web.projet.fournisseurIdentite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.projet.fournisseurIdentite.models.Sexe;

@Repository
public interface SexeRepository extends JpaRepository<Sexe,Integer> {

}