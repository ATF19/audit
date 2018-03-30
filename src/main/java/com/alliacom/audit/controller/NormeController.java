package com.alliacom.audit.controller;

import com.alliacom.audit.data.Norme;
import com.alliacom.audit.exception.ResourceNotFoundException;
import com.alliacom.audit.repository.NormeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NormeController {

    @Autowired
    NormeRepository normeRepository;

    @CrossOrigin
    @GetMapping("/normes")
    public List<Norme> getAllNormes(HttpServletResponse response) {
        List<Norme> list = normeRepository.findAll();

        /* This two headers are required in the admin-on-rest-client */
        response.addHeader("Access-Control-Expose-Headers", "X-Total-Count");
        response.addHeader("X-Total-Count", String.valueOf(list.size()));

        return list;
    }

    @CrossOrigin
    @PostMapping("/normes")
    public Norme createNorme(@Valid @RequestBody Norme norme) {
        return normeRepository.save(norme);
    }

    @CrossOrigin
    @GetMapping("/normes/{id}")
    public Norme getNormeById(@PathVariable(value = "id") Long normeId) {
        return normeRepository.findById(normeId)
                .orElseThrow(() -> new ResourceNotFoundException("Norme", "id", normeId));
    }

    @CrossOrigin
    @PutMapping("/normes/{id}")
    public Norme updateNorme(@PathVariable(value = "id") Long normeId, @Valid @RequestBody Norme normeDetails) {

        Norme norme = normeRepository.findById(normeId)
                .orElseThrow(() -> new ResourceNotFoundException("Norme", "id", normeId));

        norme.setNumero(normeDetails.getNumero());
        norme.setOrganisation(normeDetails.getOrganisation());

        return normeRepository.save(norme);
    }

    @CrossOrigin
    @DeleteMapping("/normes/{id}")
    public Norme deleteNorme(@PathVariable(value = "id") Long normeId) {

        Norme norme = normeRepository.findById(normeId)
                .orElseThrow(() -> new ResourceNotFoundException("Norme", "id", normeId));

        normeRepository.delete(norme);

        return norme;
    }
}
