package com.alliacom.audit.controller;

import com.alliacom.audit.data.Clause;
import com.alliacom.audit.exception.ResourceNotFoundException;
import com.alliacom.audit.repository.ClauseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClauseController {

    @Autowired
    ClauseRepository clauseRepository;

    @CrossOrigin
    @GetMapping("/clauses")
    public List<Clause> getClauses(HttpServletResponse response) {
        List<Clause> list = clauseRepository.findAll();

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

        clauseRepository.delete(clause);

        return clause;
    }
}
