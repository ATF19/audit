package com.alliacom.audit;

import com.alliacom.audit.data.Clause;
import com.alliacom.audit.data.Exigence;
import com.alliacom.audit.data.Responsable;
import com.alliacom.audit.repository.ClauseRepository;
import com.alliacom.audit.repository.ExigenceRepository;
import com.alliacom.audit.utilities.Rapport;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuditApplicationTests {

	@Autowired
	ClauseRepository clauseRepository;

	@Autowired
	ExigenceRepository exigenceRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void readExcelFileTest() {
		Rapport rapport = new Rapport("2018.05.06.11.32.47");
		try {
			Map<String, String> result = rapport.read();
			Object[] keys = result.keySet().toArray();
			for(int i = 0; i < keys.length; i++) {
				System.out.println(keys[i]+" = "+result.get(keys[i]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getDistinctClauses() {
		List<Clause> list = clauseRepository.findAll();
		Set<Clause> setWithoutDuplicate = new HashSet<>();
		setWithoutDuplicate.addAll(list);
		list.clear();
		list.addAll(setWithoutDuplicate);
		for (Clause clause : list) {
			System.out.println(clause.getId()+" "+clause.getLibelle());
		}
	}

}
