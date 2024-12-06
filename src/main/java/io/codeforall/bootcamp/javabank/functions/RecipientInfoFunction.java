package io.codeforall.bootcamp.javabank.functions;

import io.codeforall.bootcamp.javabank.functions.requests.RecipientInfoRequest;
import io.codeforall.bootcamp.javabank.functions.responses.RecipientInfoResponse;
import io.codeforall.bootcamp.javabank.persistence.model.Customer;

import java.util.List;
import java.util.function.Function;

public class RecipientInfoFunction implements Function<RecipientInfoRequest, RecipientInfoResponse> {

    private Customer customer;

    public RecipientInfoFunction(Customer customer) {
        this.customer = customer;
    }

    @Override
    public RecipientInfoResponse apply(RecipientInfoRequest recipientInfoRequest) {

        List<RecipientInfoResponse.Recipient> recipientsInfos = customer.getRecipients()
                .stream()
                .map(recipient -> new RecipientInfoResponse.Recipient(recipient.getId(), recipient.getAccountNumber(), recipient.getName(), recipient.getDescription()))
                .toList();

        return new RecipientInfoResponse(recipientsInfos);
    }
}
