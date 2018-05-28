package com.alliacom.audit.controller;

import com.alliacom.audit.data.Questionnaire;
import com.alliacom.audit.data.Responsable;
import com.alliacom.audit.exception.ResourceNotFoundException;
import com.alliacom.audit.repository.QuestionnaireRepository;
import com.alliacom.audit.repository.ResponsableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QuestionnaireController {

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @CrossOrigin
    @GetMapping("/questionnaires/byResponsable/{responsable_id}")
    public List<Questionnaire> getQuestionnairesByResponsable(@PathVariable(value = "responsable_id") Long responsableId) {
        List<Questionnaire> list = questionnaireRepository.allByResponsable(responsableId);
        return list;
    }

    @CrossOrigin
    @GetMapping("/questionnaires")
    public List<Questionnaire> getResponsables(HttpServletResponse response,
                                          @RequestParam(value = "_sort") Optional<String> sortBy,
                                          @RequestParam(value = "_order") Optional<String> orderDirection,
                                          @RequestParam(value = "q") Optional<String> filter) {
        List<Questionnaire> list = new ArrayList<>();

        if(sortBy.isPresent()) {
            Sort.Direction direction = Sort.Direction.ASC;
            if(orderDirection.get().equalsIgnoreCase("DESC"))
                direction = Sort.Direction.DESC;
            list = questionnaireRepository.findAll(new Sort(direction, sortBy.get()));
        }
        else {
            list = questionnaireRepository.findAll();
        }


        /* This two headers are required in the admin-on-rest-client */
        response.addHeader("Access-Control-Expose-Headers", "X-Total-Count");
        response.addHeader("X-Total-Count", String.valueOf(list.size()));

        return list;
    }

    @CrossOrigin
    @PostMapping("/questionnaires")
    public Questionnaire createQuestionnaire(@Valid @RequestBody Questionnaire questionnaire) {
        return questionnaireRepository.save(questionnaire);
    }

    @CrossOrigin
    @GetMapping("/questionnaires/{id}")
    public Questionnaire getQuestionnaireById(@PathVariable(value = "id") Long questionnaireId) {
        return questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Questionnaire", "id", questionnaireId));
    }

    @CrossOrigin
    @PutMapping("/questionnaires/{id}")
    public Questionnaire updateQuestionnaire(@PathVariable(value = "id") Long questionnaireId, @Valid @RequestBody Questionnaire questionnaireDetails) {

        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Questionnaire", "id", questionnaireId));

        questionnaire.setQuestion(questionnaire.getQuestion());

        return questionnaireRepository.save(questionnaire);
    }

    @CrossOrigin
    @DeleteMapping("/questionnaires/{id}")
    public Questionnaire deleteQuestionnaire(@PathVariable(value = "id") Long questionnaireId) {

        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Questionnaire", "id", questionnaireId));

        questionnaireRepository.delete(questionnaire);

        return questionnaire;
    }
}
