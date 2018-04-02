package com.alliacom.audit;

import com.alliacom.audit.utilities.Rapport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestClass {

    @GetMapping("/")
    public void index() {
        Rapport rapport = new Rapport("test");
        String fileName = rapport.create();
        System.out.printf(fileName);
    }
}
