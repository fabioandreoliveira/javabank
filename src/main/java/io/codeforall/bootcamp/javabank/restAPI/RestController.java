package io.codeforall.bootcamp.javabank.restAPI;

import io.codeforall.bootcamp.javabank.command.CustomerDto;
import io.codeforall.bootcamp.javabank.converters.CustomerDtoToCustomer;
import io.codeforall.bootcamp.javabank.converters.CustomerToCustomerDto;
import io.codeforall.bootcamp.javabank.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@RequestMapping("/api")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    private CustomerDto customerDto;
    private CustomerService customerService;
    private CustomerToCustomerDto customerToCustomerDto;
    private CustomerDtoToCustomer customerDtoToCustomer;

    public CustomerDto getCustomerDto() {
        return customerDto;
    }
    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }
    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerToCustomerDto getCustomerToCustomerDto() {
        return customerToCustomerDto;
    }
    @Autowired
    public void setCustomerToCustomerDto(CustomerToCustomerDto customerToCustomerDto) {
        this.customerToCustomerDto = customerToCustomerDto;
    }

    public CustomerDtoToCustomer getCustomerDtoToCustomer() {
        return customerDtoToCustomer;
    }
    @Autowired
    public void setCustomerDtoToCustomer(CustomerDtoToCustomer customerDtoToCustomer) {
        this.customerDtoToCustomer = customerDtoToCustomer;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/customer",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CustomerDto>> listCustomers() {

        List<CustomerDto> customerDtoList;
        customerDtoList = customerToCustomerDto.convert(customerService.list());

        return new ResponseEntity<>(customerDtoList, HttpStatus.OK);
    }
}