package io.codeforall.bootcamp.javabank.controller.rest;

import io.codeforall.bootcamp.javabank.services.AiService;
import org.springframework.ai.chat.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private AiService aiService;

    @Autowired
    public void setAiService(AiService aiService) {
        this.aiService = aiService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/joke")
    public ResponseEntity<String> joke() {
        return new ResponseEntity<>(aiService.joke().getOutput().getContent(), HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.POST, path = "/rag")
    public ResponseEntity<String> info(@RequestBody String question) {
        return new ResponseEntity<>(aiService.ragRequest(question).getOutput().getContent(), HttpStatus.OK);
    }
}
