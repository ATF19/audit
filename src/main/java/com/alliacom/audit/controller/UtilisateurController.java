package com.alliacom.audit.controller;

import com.alliacom.audit.data.Utilisateur;
import com.alliacom.audit.exception.ResourceNotFoundException;
import com.alliacom.audit.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UtilisateurController {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @CrossOrigin
    @GetMapping("/utilisateurs")
    public List<Utilisateur> getAllUtilisateurs(HttpServletResponse response) {
        List<Utilisateur> list = utilisateurRepository.findAll();

        /* This two headers are required in the admin-on-rest-client */
        response.addHeader("Access-Control-Expose-Headers", "X-Total-Count");
        response.addHeader("X-Total-Count", String.valueOf(list.size()));

        return list;
    }

    @CrossOrigin
    @PostMapping("/utilisateurs")
    public Utilisateur createUtilisateur(@Valid @RequestBody Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @CrossOrigin
    @GetMapping("/utilisateurs/{id}")
    public Utilisateur getUtilisateurById(@PathVariable(value = "id") Long utilisateurId) {
        return utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", utilisateurId));
    }

    @CrossOrigin
    @PutMapping("/utilisateurs/{id}")
    public Utilisateur updateUtilisateur(@PathVariable(value = "id") Long utilisateurId, @Valid @RequestBody Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", utilisateurId));

        utilisateur.setEmail(utilisateurDetails.getEmail());
        utilisateur.setPassword(utilisateurDetails.getPassword());
        utilisateur.setRole(utilisateurDetails.getRole());

        return utilisateurRepository.save(utilisateur);
    }

    @CrossOrigin
    @DeleteMapping("/utilisateurs/{id}")
    public Utilisateur deleteUtilisateur(@PathVariable(value = "id") Long utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", utilisateurId));

        utilisateurRepository.delete(utilisateur);

        return utilisateur;
    }

    @CrossOrigin
    @PostMapping("/utilisateurs/login")
    public ResponseEntity<?> login(@Valid @RequestBody Utilisateur givenUtilisateur) {
        boolean found = false;

        List<Utilisateur> list = utilisateurRepository.findAll();

        for(int i=0; i < list.size(); i++) {
            Utilisateur currentUtilisateur = list.get(i);
            if(currentUtilisateur.login(givenUtilisateur)) {
                found = true;
                break;
            }
        }

        if(found)
            return ResponseEntity.ok().build();

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @PostMapping("/utilisateurs/admin/login")
    public ResponseEntity<?> loginAdmin(@Valid @RequestBody Utilisateur givenUtilisateur) {
        boolean found = false;

        List<Utilisateur> list = utilisateurRepository.findAll();

        for(int i=0; i < list.size(); i++) {
            Utilisateur currentUtilisateur = list.get(i);
            if(currentUtilisateur.loginAdmin(givenUtilisateur)) {
                found = true;
                break;
            }
        }

        if(found) {
            Map<String, String> responseObject = new HashMap<String ,String>();

            // TODO
            responseObject.put("token", "a");

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");

            return new ResponseEntity<Map<String, String>>(responseObject, headers, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
