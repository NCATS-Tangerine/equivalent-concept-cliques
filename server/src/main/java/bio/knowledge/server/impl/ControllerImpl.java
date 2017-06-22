package bio.knowledge.server.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ControllerImpl {
	
	private static final long TIMEOUT = 1;
	private static final TimeUnit TIMEUNIT = TimeUnit.MINUTES;

	@Autowired
	KnowledgeBeaconService kbs;
	
	private String fixString(String str) {
		return str != null ? str : "";
	}
	
	public ResponseEntity<List<String>> getExactMatchesToConcept(String conceptId) {		
		try {
			
			conceptId = fixString(conceptId);
			List<String> response = kbs.getExactMatchesToConcept(conceptId).get(TIMEOUT, TIMEUNIT);
			return ResponseEntity.ok(response);

			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e.getCause());
		}
	}

}
