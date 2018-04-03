package com.alliacom.audit.controller;

import com.alliacom.audit.data.Clause;
import com.alliacom.audit.data.Exigence;
import com.alliacom.audit.data.Responsable;
import com.alliacom.audit.exception.ResourceNotFoundException;
import com.alliacom.audit.repository.ClauseRepository;
import com.alliacom.audit.repository.ExigenceRepository;
import com.alliacom.audit.repository.ResponsableRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ExigenceController {

    @Autowired
    ExigenceRepository exigenceRepository;

    @Autowired
    ResponsableRepository responsableRepository;

    @Autowired
    ClauseRepository clauseRepository;

    @CrossOrigin
    @GetMapping("/exigences")
    public List<Exigence> getAllExigences(HttpServletResponse response,
                                          @RequestParam(value = "responsables") Optional<List<Long>> responsablesIdsOptional,
                                          @RequestParam(value = "clause") Optional<List<Long>> clausesIdsOptional) {

        List<Exigence> list;


        if(responsablesIdsOptional.isPresent() && clausesIdsOptional.isPresent()) {
            List<Long> clausesIds = clausesIdsOptional.get();
            List<Long> responsablesIds= responsablesIdsOptional.get();
            List<Clause> clauses = clauseRepository.findAllById(clausesIds);
            List<Responsable> responsables = responsableRepository.findAllById(responsablesIds);
            list = exigenceRepository.findDistinctByClauseInAndResponsablesIn(clauses, responsables);

        }
        else {
            list = exigenceRepository.findAll();
        }

        /* This two headers are required in the admin-on-rest-client */
        response.addHeader("Access-Control-Expose-Headers", "X-Total-Count");
        response.addHeader("X-Total-Count", String.valueOf(list.size()));

        return list;
    }


    @CrossOrigin
    @PostMapping("/exigences")
    public Exigence createExigence(@Valid @RequestBody Map<String, Object> body) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();

        List<String> responsablesIds = null;
        List<Responsable> responsables = new ArrayList<>();

        LinkedHashMap clauseMap = (LinkedHashMap) body.get("clause");
        Long clauseId = Long.valueOf((Integer) clauseMap.get("id"));

        try {
            responsablesIds = objectMapper.readValue(
                    body.get("responsables").toString(),
                    typeFactory.constructCollectionType(List.class, String.class)
            );
            for(int i=0; i < responsablesIds.size(); i++) {
                Long id = Long.valueOf(responsablesIds.get(i));
                Responsable responsable = responsableRepository.findById(id)
                        .orElse(new Responsable());
                responsables.add(responsable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Clause clause = clauseRepository.findById(clauseId)
                .orElse(new Clause());

        Exigence exigence = new Exigence();
        exigence.setLibelle((String) body.get("libelle"));
        exigence.setReference((String) body.get("reference"));
        exigence.setClause(clause);
        exigence.setResponsables(responsables);

        return exigenceRepository.save(exigence);
    }

    @CrossOrigin
    @GetMapping("/exigences/{id}")
    public Exigence getExigenceById(@PathVariable(value = "id") Long exigenceId) {
        return exigenceRepository.findById(exigenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Exigence", "id", exigenceId));
    }

    @CrossOrigin
    @PutMapping("/exigences/{id}")
    public Exigence updateExigence(@PathVariable(value = "id") Long exigenceId, @Valid @RequestBody Exigence exigenceDetails) {
        Exigence exigence = exigenceRepository.findById(exigenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Exigence", "id", exigenceId));

        exigence.setLibelle(exigenceDetails.getLibelle());
        exigence.setReference(exigenceDetails.getReference());

        return exigenceRepository.save(exigence);
    }

    @CrossOrigin
    @DeleteMapping("/exigences/{id}")
    public Exigence deleteExigence(@PathVariable(value = "id") Long exigenceId) {
        Exigence exigence = exigenceRepository.findById(exigenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", exigenceId));

        exigenceRepository.delete(exigence);

        return exigence;
    }
}
