package io.codeforall.bootcamp.javabank.controller.rest;

import io.codeforall.bootcamp.javabank.services.AiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class RestAiController {

    private AiService aiService;

    @Autowired
    public void setAiService(AiService aiService) {
        this.aiService = aiService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/joke")
    public ResponseEntity<String> joke() {
        return new ResponseEntity<>(aiService.joke().getOutput().getContent(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/info")
    public ResponseEntity<String> info(@Valid @RequestBody String question, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(aiService.info(question).getOutput().getContent(), HttpStatus.OK);
    }

}
