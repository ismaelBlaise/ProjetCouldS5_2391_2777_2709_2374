package web.projet.fournisseurIdentite.controllers;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.services.UtilisateurService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping
    public UtilisateurDTO create(@RequestBody UtilisateurDTO data) {
        return utilisateurService.save(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> update(@PathVariable Integer id, @RequestBody UtilisateurDTO data) {
        return ResponseEntity.ok(utilisateurService.update(id, data));
    }

    @PostMapping("/inscrire")
    public ResponseEntity<String> inscrireUtilisateur(@RequestBody UtilisateurDTO dto) {
        try {
            utilisateurService.inscrireUtilisateur(dto);
            return ResponseEntity.ok("Inscription réussie, veuillez vérifier votre email.");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
                
        
    }

    @GetMapping("/valider")
    public ResponseEntity<String> validerCompte(@RequestParam String token) {
        try {
            utilisateurService.validerCompte(token);
            return ResponseEntity.ok("Compte validé avec succès !");
        } catch (Exception e) {
            return  ResponseEntity.ok(e.getMessage());
        }
    }

}
