package web.projet.fournisseurIdentite.models;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "utilisateurs")
@Data
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateurs")
    private Long id;

    private String email;
    private String nom;
    private String prenom;
    private LocalDate date_naissance;
    private String mot_de_passe;
    private Boolean etat = false;
    private Integer nb_tentative = 0;

    @ManyToOne
    @JoinColumn(name = "id_sexe")
    private Sexe sexe;
}
