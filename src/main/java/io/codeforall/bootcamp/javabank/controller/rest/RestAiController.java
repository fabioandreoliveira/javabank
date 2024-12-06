package io.codeforall.bootcamp.javabank.controller.rest;

import io.codeforall.bootcamp.javabank.persistence.model.Customer;
import io.codeforall.bootcamp.javabank.services.AiService;
import io.codeforall.bootcamp.javabank.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class RestAiController {

    private CustomerService customerService;
    private AiService aiService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setAiService(AiService aiService) {
        this.aiService = aiService;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/joke")
    public ResponseEntity<String> joke() {
        return new ResponseEntity<>(aiService.joke().getOutput().getContent(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/info")
    public ResponseEntity<String> info(@Valid @RequestBody String question, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(aiService.info(question).getOutput().getContent(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/customer/{cid}")
    public ResponseEntity<String> customer(@Valid @RequestBody String question, BindingResult bindingResult, @PathVariable Integer cid) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Customer customer = customerService.get(cid);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(aiService.customerInfo(customer, question).getOutput().getContent(),HttpStatus.OK);

    }
}
