package io.codeforall.bootcamp.javabank.functions.responses;

import java.util.List;
import java.util.Objects;

public class RecipientInfoResponse {

    List<Recipient> recipients;

    public RecipientInfoResponse(List<Recipient> recipients) {
        this.recipients = recipients;
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public static class Recipient {

        private Integer id;
        private Integer accountNumber;
        private String name;
        private String description;

        public Recipient(Integer id, Integer accountNumber, String name, String description) {
            this.id = id;
            this.accountNumber = accountNumber;
            this.name = name;
            this.description = description;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(Integer accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Recipient recipient = (Recipient) o;
            return Objects.equals(id, recipient.id) && Objects.equals(accountNumber, recipient.accountNumber) && Objects.equals(name, recipient.name) && Objects.equals(description, recipient.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, accountNumber, name, description);
        }
    }
}
