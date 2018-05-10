package com.alliacom.audit.controller;

import com.alliacom.audit.data.Norme;
import com.alliacom.audit.exception.ResourceNotFoundException;
import com.alliacom.audit.repository.ExigenceRepository;
import com.alliacom.audit.repository.NormeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NormeController {

    @Autowired
    NormeRepository normeRepository;

    @Autowired
    ExigenceRepository exigenceRepository;

    @CrossOrigin
    @GetMapping("/normes")
    public List<Norme> getAllNormes(HttpServletResponse response,
        @RequestParam(value = "_sort") Optional<String> sortBy,
        @RequestParam(value = "_order") Optional<String> orderDirection,
        @RequestParam(value = "q") Optional<String> filter
    ) {
        List<Norme> list = new ArrayList<>();
        if(sortBy.isPresent()) {
            Sort.Direction direction = Sort.Direction.ASC;
            if(orderDirection.get().equalsIgnoreCase("DESC"))
                direction = Sort.Direction.DESC;
            list = normeRepository.findAll(new Sort(direction, sortBy.get()));
        }
        else {
            list = normeRepository.findAll();
        }

        if(filter.isPresent()) {
            list = normeRepository.findAllByOrganisationContains(filter.get());
        }

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

        norme.delete(exigenceRepository);
        normeRepository.delete(norme);

        return norme;
    }
}
