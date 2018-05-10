package com.alliacom.audit.controller;

import com.alliacom.audit.data.Token;
import com.alliacom.audit.data.Utilisateur;
import com.alliacom.audit.exception.ResourceNotFoundException;
import com.alliacom.audit.repository.TokenRepository;
import com.alliacom.audit.repository.UtilisateurRepository;
import com.alliacom.audit.utilities.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UtilisateurController {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    TokenRepository tokenRepository;

    @CrossOrigin
    @GetMapping("/utilisateurs")
    public List<Utilisateur> getAllUtilisateurs(HttpServletResponse response,
                                                @RequestParam(value = "_sort") Optional<String> sortBy,
                                                @RequestParam(value = "_order") Optional<String> orderDirection,
                                                @RequestParam(value = "q") Optional<String> filter) {
        List<Utilisateur> list = new ArrayList<>();

        if(sortBy.isPresent()) {
            Sort.Direction direction = Sort.Direction.ASC;
            if(orderDirection.get().equalsIgnoreCase("DESC"))
                direction = Sort.Direction.DESC;
            list = utilisateurRepository.findAll(new Sort(direction, sortBy.get()));
        }
        else {
            list = utilisateurRepository.findAll();
        }

        if(filter.isPresent()) {
            list = utilisateurRepository.findAllByEmailContains(filter.get());
        }

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

        utilisateur.delete();
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

        if(found) {
            Map<String, String> responseObject = new HashMap<String ,String>();

            TokenUtil tokenUtil = new TokenUtil();
            String tokenString = tokenUtil.generate();
            // Set the expire date
            Date now = Date.from(Instant.now());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.DATE, 7);
            Date expirationDate = calendar.getTime();

            Utilisateur utilisateur = utilisateurRepository.findByEmailEquals(givenUtilisateur.getEmail());


            Token token = new Token();
            token.setUtilisateur(utilisateur);
            token.setToken(tokenString);
            token.setExpireDate(expirationDate);

            tokenRepository.save(token);

            responseObject.put("token", tokenString);
            responseObject.put("utilisateurId", String.valueOf(utilisateur.getId()));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");

            return new ResponseEntity<Map<String, String>>(responseObject, headers, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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

            TokenUtil tokenUtil = new TokenUtil();
            String tokenString = tokenUtil.generate();
            // Set the expire date
            Date now = Date.from(Instant.now());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.DATE, 7);
            Date expirationDate = calendar.getTime();

            Utilisateur utilisateur = utilisateurRepository.findByEmailEquals(givenUtilisateur.getEmail());


            Token token = new Token();
            token.setUtilisateur(utilisateur);
            token.setToken(tokenString);
            token.setExpireDate(expirationDate);

            tokenRepository.save(token);

            responseObject.put("token", tokenString);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");

            return new ResponseEntity<Map<String, String>>(responseObject, headers, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
