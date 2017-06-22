package bio.knowledge.server.api;


import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import bio.knowledge.server.impl.ControllerImpl;

import java.util.List;

import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-15T14:47:52.567-07:00")

@Controller
public class ExactmatchesApiController implements ExactmatchesApi {

	@Autowired ControllerImpl ctrl;

    public ResponseEntity<List<String>> getExactMatchesToConcept(@ApiParam(value = "(url-encoded) CURIE identifier of the concept to be matched",required=true ) @PathVariable("conceptId") String conceptId) {
        // do some magic!
    	return ctrl.getExactMatchesToConcept(conceptId);    }

}
