package io.codeforall.bootcamp.javabank.functions.responses;

import java.util.Objects;

public class CustomerInfoResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer numberOfAccounts;
    private Double totalBalance;


    public CustomerInfoResponse(String firstName, String lastName, String email, String phone, Integer numberOfAccounts, Double totalBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.numberOfAccounts = numberOfAccounts;
        this.totalBalance = totalBalance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getNumberOfAccounts() {
        return numberOfAccounts;
    }

    public void setNumberOfAccounts(Integer numberOfAccounts) {
        this.numberOfAccounts = numberOfAccounts;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerInfoResponse that = (CustomerInfoResponse) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(numberOfAccounts, that.numberOfAccounts) && Objects.equals(totalBalance, that.totalBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, phone, numberOfAccounts, totalBalance);
    }
}
