package com.alliacom.audit.controller;

import com.alliacom.audit.data.Responsable;
import com.alliacom.audit.exception.ResourceNotFoundException;
import com.alliacom.audit.repository.ResponsableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ResponsableController {

    @Autowired
    ResponsableRepository responsableRepository;

    @CrossOrigin
    @GetMapping("/responsables")
    public List<Responsable> getAllResponsables(HttpServletResponse response) {
        List<Responsable> list = responsableRepository.findAll();

        /* This two headers are required in the admin-on-rest-client */
        response.addHeader("Access-Control-Expose-Headers", "X-Total-Count");
        response.addHeader("X-Total-Count", String.valueOf(list.size()));

        return list;
    }

    @CrossOrigin
    @PostMapping("/responsables")
    public Responsable createResponsable(@Valid @RequestBody Responsable responsable) {
        return responsableRepository.save(responsable);
    }

    @CrossOrigin
    @GetMapping("/responsables/{id}")
    public Responsable getResponsableById(@PathVariable(value = "id") Long responsableId) {
        return responsableRepository.findById(responsableId)
                .orElseThrow(() -> new ResourceNotFoundException("Responsable", "id", responsableId));
    }

    @CrossOrigin
    @PutMapping("/responsables/{id}")
    public Responsable updateResponsable(@PathVariable(value = "id") Long responsableId, @Valid @RequestBody Responsable responsableDetails) {
        Responsable responsable = responsableRepository.findById(responsableId)
                .orElseThrow(() -> new ResourceNotFoundException("Responsable", "id", responsableId));

        responsable.setTitre(responsableDetails.getTitre());

        return responsableRepository.save(responsable);
    }

    @CrossOrigin
    @DeleteMapping("/responsables/{id}")
    public Responsable deleteResponsable(@PathVariable(value = "id") Long responsableId) {
        Responsable responsable = responsableRepository.findById(responsableId)
                .orElseThrow(() -> new ResourceNotFoundException("Responsable", "id", responsableId));

        responsableRepository.delete(responsable);

        return responsable;
    }
}
