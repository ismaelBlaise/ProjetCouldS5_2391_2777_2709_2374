package web.projet.fournisseurIdentite.controllers;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.ValidationPinDTO;
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

    @PostMapping("/validation-pin")
    public ResponseEntity<String> validationPin(@RequestBody ValidationPinDTO validationPinDTO) {
        try {
            utilisateurService.validationPin(validationPinDTO);
            return ResponseEntity.ok("Code PIN validé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
