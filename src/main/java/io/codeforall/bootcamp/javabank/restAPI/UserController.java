package io.codeforall.bootcamp.javabank.controller;

import io.codeforall.bootcamp.javabank.command.CustomerDto;
import io.codeforall.bootcamp.javabank.persistence.model.account.Account;
import io.codeforall.bootcamp.javabank.services.CustomerService;
import io.codeforall.bootcamp.javabank.converters.AccountToAccountDto;
import io.codeforall.bootcamp.javabank.converters.CustomerDtoToCustomer;
import io.codeforall.bootcamp.javabank.converters.CustomerToCustomerDto;
import io.codeforall.bootcamp.javabank.converters.RecipientToRecipientDto;
import io.codeforall.bootcamp.javabank.exceptions.AssociationExistsException;
import io.codeforall.bootcamp.javabank.exceptions.CustomerNotFoundException;
import io.codeforall.bootcamp.javabank.exceptions.JavaBankException;
import io.codeforall.bootcamp.javabank.persistence.model.Customer;
import io.codeforall.bootcamp.javabank.services.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * Controller responsible for rendering {@link Customer} related views
 */
@Controller
public class UserController {

    private CustomerService customerService;

    private CustomerToCustomerDto customerToCustomerDto;
    private CustomerDtoToCustomer customerDtoToCustomer;
    private AccountToAccountDto accountToAccountDto;
    private RecipientToRecipientDto recipientToRecipientDto;
    private String id;
    private CustomerServiceImpl accountService;


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
     * Sets the converter for converting between customer model objects and customer form objects
     *
     * @param customerToCustomerDto the customer to customer form converter to set
     */
    @Autowired
    public void setCustomerToCustomerDto(CustomerToCustomerDto customerToCustomerDto) {
        this.customerToCustomerDto = customerToCustomerDto;
    }

    /**
     * Sets the converter for converting between customer form and customer model objects
     *
     * @param customerDtoToCustomer the customer form to customer converter to set
     */
    @Autowired
    public void setCustomerDtoToCustomer(CustomerDtoToCustomer customerDtoToCustomer) {
        this.customerDtoToCustomer = customerDtoToCustomer;
    }

    /**
     * Sets the converter for converting between account objects and account dto objects
     *
     * @param accountToAccountDto the account to account dto converter to set
     */
    @Autowired
    public void setAccountToAccountDto(AccountToAccountDto accountToAccountDto) {
        this.accountToAccountDto = accountToAccountDto;
    }

    /**
     * Sets the converter for converting between recipient model objects and recipient form objects
     *
     * @param recipientToRecipientDto the recipient to recipient form converter to set
     */
    @Autowired
    public void setRecipientToRecipientDto(RecipientToRecipientDto recipientToRecipientDto) {
        this.recipientToRecipientDto = recipientToRecipientDto;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/customer/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity listCustomers() {
        return new ResponseEntity<>(customerToCustomerDto.convert(customerService.list()), HttpStatus.OK);
    }
    @RequestMapping(
            method = RequestMethod.POST,
            value = "api/addCustomer",
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity addUser(@Valid @RequestBody CustomerDto customer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(customerService.save(customerDtoToCustomer.convert(customer)),HttpStatus.CREATED);


    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "api/customer/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity getCustomer(@PathVariable Integer id) {
        return new ResponseEntity<>(customerToCustomerDto.convert(customerService.get(id)),HttpStatus.OK);
    }
    @RequestMapping(
            method = RequestMethod.GET,
            value = "api/customer/{id}/recipients",
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity getRecipients(@PathVariable Integer id) {
        return new ResponseEntity<>(recipientToRecipientDto.convert(customerService.listRecipients(id)),HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "api/customer/{id}/accounts",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity getAccounts(@PathVariable Integer id) {


        return new ResponseEntity<>(accountToAccountDto.convert(customerService.get(id).getAccounts()),HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "api/customer/{id}/accounts/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity getAccountId(@PathVariable Integer id, @PathVariable Integer accountId) {

        Customer customer = customerService.get(id);

        List<Account> accounts = customer.getAccounts();

        for (Account account : accounts) {
            if (Objects.equals(account.getId(), accountId)) {
                return new ResponseEntity<>(accountToAccountDto.convert(account),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "api/customer/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity deleteCustomer(@PathVariable Integer id) {
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "api/customer/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity updateCustomer(@PathVariable Integer id, @Valid @RequestBody CustomerDto customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Customer existingCustomer = customerService.get(id);
        if (existingCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerToCustomerDto.convert(existingCustomer);
        return new ResponseEntity<>(customerService.save(existingCustomer), HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "api/customer",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity addCustomer(@Valid @RequestBody CustomerDto customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerService.save(customerDtoToCustomer.convert(customer)),HttpStatus.CREATED);
    }
   /* @RequestMapping(
            method = RequestMethod.DELETE,
            value = "api/customer/{id}/account/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity deleteAccount(@PathVariable Integer id, @PathVariable Integer accountId) {
        customerService.deleteAccount(id, accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/


}
