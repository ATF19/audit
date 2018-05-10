package com.alliacom.audit.controller;

import com.alliacom.audit.data.*;
import com.alliacom.audit.exception.ResourceNotFoundException;
import com.alliacom.audit.repository.AnalyseRepository;
import com.alliacom.audit.repository.ExigenceRepository;
import com.alliacom.audit.repository.NormeRepository;
import com.alliacom.audit.repository.UtilisateurRepository;
import com.alliacom.audit.utilities.Rapport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
public class AnalyseController {

    @Autowired
    AnalyseRepository analyseRepository;

    @Autowired
    ExigenceRepository exigenceRepository;

    @Autowired
    NormeRepository normeRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @CrossOrigin
    @GetMapping("/analyses")
    public List<Analyse> getAnalyses(HttpServletResponse response,
                                     @RequestParam(value = "_sort") Optional<String> sortBy,
                                     @RequestParam(value = "_order") Optional<String> orderDirection,
                                     @RequestParam(value = "q") Optional<String> filter) {
        List<Analyse> list = new ArrayList<>();

        if(sortBy.isPresent()) {
            Sort.Direction direction = Sort.Direction.ASC;
            if(orderDirection.get().equalsIgnoreCase("DESC"))
                direction = Sort.Direction.DESC;
            list = analyseRepository.findAll(new Sort(direction, sortBy.get()));
        }
        else {
            list = analyseRepository.findAll();
        }

        if(filter.isPresent()) {
            List<Analyse> newList = new ArrayList<>();
            for(int i=0; i<list.size(); i++) {
                if(list.get(i).getUtilisateur().getEmail().toLowerCase().indexOf(filter.get().toLowerCase()) >= 0) {
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
    @PostMapping("/analyses")
    public @ResponseBody
    ResponseEntity createAnalyse(@Valid @RequestBody Map<String, Object> body) {

        Long normeId = new Long((Integer) body.get("normeId"));
        Long utilisateurId = new Long((Integer) body.get("utilisateurId"));
        String graphe = (String) body.get("graphe");
        String client = (String) body.get("client");

        Norme norme = normeRepository.findById(normeId)
                .orElse(null);
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElse(null);
        String normeName = norme.getOrganisation()+" "+norme.getNumero();


        List<Map<String, Object>> exigences = (List<Map<String, Object>>) body.get("exigences");

        int datasLength = exigences.size();
        Object[][] datas = new Object[datasLength][];
        int datasCounter = 0;


        for(int i=0; i<datasLength; i++) {
            Map<String, Object> element = exigences.get(i);
            Long exigenceId = new Long((Integer) element.get("exigenceId"));
            int note = (Integer) element.get("note");

            Exigence exigence = exigenceRepository.findById(exigenceId)
                    .orElse(null);

            Object[] line = {
                    normeName,
                    exigence.getReference(),
                    exigence.getLibelle(),
                    note
            };

            datas[datasCounter] = line;
            datasCounter++;

        }

        // Used in the file name
        String currentTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                .format(new Date());

        Rapport rapport = new Rapport(currentTime);
        rapport.setGrapheBase64(graphe);
        rapport.setClient(client);
        rapport.generateGrapheImage();
        String fileName = rapport.create(datas);

        Analyse analyse = new Analyse();
        analyse.setNorme(norme);
        analyse.setUtilisateur(utilisateur);
        analyse.setRapport(fileName);

        analyseRepository.save(analyse);


        Map<String, String> responseObject = new HashMap<String ,String>();
        responseObject.put("rapport", analyse.getRapport());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        return new ResponseEntity<Map<String, String>>(responseObject, headers, HttpStatus.OK);
    }


    @CrossOrigin
    @DeleteMapping("/analyses/{id}")
    public Analyse deleteAnalyse(@PathVariable(value = "id") Long analyseId) {

        Analyse analyse = analyseRepository.findById(analyseId)
                .orElseThrow(() -> new ResourceNotFoundException("Analyse", "id", analyseId));

        analyse.delete();
        analyseRepository.delete(analyse);

        return analyse;
    }

    @CrossOrigin
    @GetMapping("/rapports/{filename}")
    public @ResponseBody ResponseEntity downloadRapport(@PathVariable(value = "filename") String fileName) {
        String base_path = Rapport.RAPPORT_DIRECTORY;
        String file = base_path + fileName;
        try {
            //InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            Path path = Paths.get(file);

            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + fileName)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.parseMediaType("application/xls"))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
