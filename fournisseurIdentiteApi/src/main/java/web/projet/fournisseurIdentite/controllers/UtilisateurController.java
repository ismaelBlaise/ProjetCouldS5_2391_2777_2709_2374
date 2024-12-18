package web.projet.fournisseurIdentite.controllers;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurUpdateDTO;
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
    public ResponseEntity<UtilisateurDTO> update(@PathVariable Long id, @RequestBody UtilisateurUpdateDTO data) {
        return ResponseEntity.ok(utilisateurService.update(id, data));
    }
    

    @PostMapping("/inscrire")
    public ResponseEntity<?> inscrireUtilisateur(@RequestBody UtilisateurDTO dto){
        try {
            String url=utilisateurService.inscrireUtilisateur(dto);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/valider-compte")
    public ResponseEntity<String> validerCompte(@RequestParam String token) {
        try {
            utilisateurService.validerCompte(token);
            return ResponseEntity.ok("Compte validé avec succès !");
        } catch (Exception e) {
            return  ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/reinitialiser-tentative")
    public ResponseEntity<String> reinitialiserTentative(@RequestParam String token) {
        try {
            utilisateurService.reinitialiserTentative(token);
            return ResponseEntity.ok("Compte validé avec succès !");
        } catch (Exception e) {
            return  ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/validation-pin")
    public ResponseEntity<String> validationPin(@RequestBody ValidationPinDTO validationPinDTO) {
        try {
            String tokens=utilisateurService.validationPin(validationPinDTO);
            if(tokens==null){
                throw new RuntimeException("Code PIN incorrect.");
            }
            return ResponseEntity.ok(tokens);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Code pin non valide");
        }
    }
}
