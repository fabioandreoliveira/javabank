package io.codeforall.bootcamp.javabank.controller.rest;

import io.codeforall.bootcamp.javabank.command.RecipientDto;
import io.codeforall.bootcamp.javabank.converters.RecipientToRecipientDto;
import io.codeforall.bootcamp.javabank.services.CustomerService;
import io.codeforall.bootcamp.javabank.services.RecipientService;
import io.codeforall.bootcamp.javabank.converters.RecipientDtoToRecipient;
import io.codeforall.bootcamp.javabank.exceptions.AccountNotFoundException;
import io.codeforall.bootcamp.javabank.exceptions.CustomerNotFoundException;
import io.codeforall.bootcamp.javabank.exceptions.RecipientNotFoundException;
import io.codeforall.bootcamp.javabank.persistence.model.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller responsible for {@link Recipient} related CRUD operations
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class RestRecipientController {

    private CustomerService customerService;
    private RecipientService recipientService;
    private RecipientToRecipientDto recipientToRecipientDto;
    private RecipientDtoToRecipient recipientDtotoRecipient;

    /**
     * Sets the customer service
     *
     * @param customerService the customer service to set
     */
    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Sets the recipient service
     *
     * @param recipientService the recipient service to set
     */
    @Autowired
    public void setRecipientService(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    /**
     * Sets the converter for converting between recipient model objects and recipient DTO
     *
     * @param recipientToRecipientDto the recipient to recipient DTO converter
     */
    @Autowired
    public void setRecipientToRecipientDto(RecipientToRecipientDto recipientToRecipientDto) {
        this.recipientToRecipientDto = recipientToRecipientDto;
    }

    /**
     * Sets the converter for converting between recipient DTO and recipient model objects
     *
     * @param recipientDtoToRecipient the recipient DTO to recipient converter
     */
    @Autowired
    public void setRecipientDtotoRecipient(RecipientDtoToRecipient recipientDtoToRecipient) {
        this.recipientDtotoRecipient = recipientDtoToRecipient;
    }

    /**
     * Retrieves a representation of the list of customer recipients
     *
     * @param cid the customer id
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{cid}/recipient")
    public ResponseEntity<List<RecipientDto>> listRecipients(@PathVariable Integer cid) {

        try {

            List<RecipientDto> recipientDtos = customerService.listRecipients(cid).stream()
                    .map(recipient -> recipientToRecipientDto.convert(recipient))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(recipientDtos, HttpStatus.OK);

        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a representation of a customer recipient
     *
     * @param cid the customer id
     * @param id  the recipient id
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{cid}/recipient/{id}")
    public ResponseEntity<RecipientDto> showRecipient(@PathVariable Integer cid, @PathVariable Integer id) {

        Recipient recipient = recipientService.get(id);

        if (recipient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(recipientToRecipientDto.convert(recipient), HttpStatus.OK);
    }

    /**
     * Adds a recipient
     *
     * @param cid                  the customer id
     * @param recipientDto         the recipient DTO
     * @param bindingResult        the binding result object
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.POST, path = "/{cid}/recipient")
    public ResponseEntity<?> addRecipient(@PathVariable Integer cid, @Valid @RequestBody RecipientDto recipientDto, BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder) {

        if (bindingResult.hasErrors() || recipientDto.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {

            Recipient recipient = customerService.addRecipient(cid, recipientDtotoRecipient.convert(recipientDto));

            UriComponents uriComponents = uriComponentsBuilder.path("/api/customer/" + cid + "/recipient/" + recipient.getId()).build();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponents.toUri());

            return new ResponseEntity<>(headers, HttpStatus.CREATED);

        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Edits a recipient
     *
     * @param cid           the customer id
     * @param id            the recipient id
     * @param recipientDto  the recipient DTO
     * @param bindingResult the binding result object
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/{cid}/recipient/{id}")
    public ResponseEntity<RecipientDto> editRecipient(@PathVariable Integer cid, @PathVariable Integer id, @Valid @RequestBody RecipientDto recipientDto, BindingResult bindingResult) {

        try {

            if (bindingResult.hasErrors()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (recipientDto.getId() != null && recipientDto.getId() != id) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            recipientDto.setId(id);

            Recipient savedRecipient = recipientDtotoRecipient.convert(recipientDto);
            customerService.addRecipient(cid, savedRecipient);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a recipient
     *
     * @param cid the customer id
     * @param id  the recipient id
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/{cid}/recipient/{id}")
    public ResponseEntity<?> deleteRecipient(@PathVariable Integer cid, @PathVariable Integer id) {

        try {

            customerService.removeRecipient(cid, id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (RecipientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
