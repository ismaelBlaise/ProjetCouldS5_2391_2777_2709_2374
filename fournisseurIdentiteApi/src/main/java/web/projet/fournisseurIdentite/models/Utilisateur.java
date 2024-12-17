package web.projet.fournisseurIdentite.models;

import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@SuppressWarnings("unused")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_utilisateur")
   private Integer id;

   @Column(name = "email")
   private String email;

   @Column(name = "nom")
   private String nom;

   @Column(name = "prenom")
   private String prenom;

   @Column(name = "date_naissance")
   private LocalDate dateNaissance;

   @Column(name = "mot_de_passe")
   private String passe;

   @ManyToOne
   @JoinColumn(name = "id_sexe")
   private Sexe sexe;

}