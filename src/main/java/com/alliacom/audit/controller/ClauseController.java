package com.alliacom.audit.controller;

import com.alliacom.audit.data.Clause;
import com.alliacom.audit.data.Exigence;
import com.alliacom.audit.exception.ResourceNotFoundException;
import com.alliacom.audit.repository.ClauseRepository;
import com.alliacom.audit.repository.ExigenceRepository;
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
public class ClauseController {

    @Autowired
    ClauseRepository clauseRepository;

    @Autowired
    ExigenceRepository exigenceRepository;

    @CrossOrigin
    @GetMapping("/clauses")
    public List<Clause> getClauses(HttpServletResponse response,
                                   @RequestParam(value = "_sort") Optional<String> sortBy,
                                   @RequestParam(value = "_order") Optional<String> orderDirection,
                                   @RequestParam(value = "q") Optional<String> filter) {
        List<Clause> list = new ArrayList<>();

        if(sortBy.isPresent()) {
            Sort.Direction direction = Sort.Direction.ASC;
            if(orderDirection.get().equalsIgnoreCase("DESC"))
                direction = Sort.Direction.DESC;
            list = clauseRepository.findAll(new Sort(direction, sortBy.get()));
        }
        else {
            list = clauseRepository.findAll();
        }

        if(filter.isPresent()) {
            List<Clause> newList = new ArrayList<>();
            for(int i=0; i<list.size(); i++) {
                String normeName = list.get(i).getNorme().getOrganisation() + " " + list.get(i).getNorme().getNumero();
                normeName = normeName.toLowerCase();
                if(normeName.indexOf(filter.get().toLowerCase()) >= 0) {
                    newList.add(list.get(i));
                }
            }
            list = newList;
        }

        /* This two headers are required in the admin-on-rest-client */
        response.addHeader("Access-Control-Expose-Headers", "X-Total-Count");
        response.addHeader("X-Total-Count", String.valueOf(list.size()));

        return list;
    }

    @CrossOrigin
    @PostMapping("/clauses")
    public Clause createClause(@Valid @RequestBody Clause clause) {
        return clauseRepository.save(clause);
    }

    @CrossOrigin
    @GetMapping("/clauses/{id}")
    public Clause getClauseById(@PathVariable(value = "id") Long clauseId) {
        return clauseRepository.findById(clauseId)
                .orElseThrow(() -> new ResourceNotFoundException("Clause", "id", clauseId));
    }

    @CrossOrigin
    @PutMapping("/clauses/{id}")
    public Clause updateClause(@PathVariable(value = "id") Long clauseId, @Valid @RequestBody Clause clauseDetails) {

        Clause clause = clauseRepository.findById(clauseId)
                .orElseThrow(() -> new ResourceNotFoundException("Clause", "id", clauseId));

        clause.setLibelle(clauseDetails.getLibelle());
        clause.setNorme(clauseDetails.getNorme());

        return clauseRepository.save(clause);
    }

    @CrossOrigin
    @DeleteMapping("/clauses/{id}")
    public Clause deleteClause(@PathVariable(value = "id") Long clauseId) {

        Clause clause = clauseRepository.findById(clauseId)
                .orElseThrow(() -> new ResourceNotFoundException("Clause", "id", clauseId));

        clause.delete(exigenceRepository);
        clauseRepository.delete(clause);

        return clause;
    }
}
