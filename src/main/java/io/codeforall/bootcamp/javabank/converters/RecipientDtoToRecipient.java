package io.codeforall.bootcamp.javabank.converters;

import io.codeforall.bootcamp.javabank.command.RecipientDto;
import io.codeforall.bootcamp.javabank.exceptions.RecipientNotFoundException;
import io.codeforall.bootcamp.javabank.services.RecipientService;
import io.codeforall.bootcamp.javabank.persistence.model.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A {@link Converter} implementation, responsible for {@link RecipientDto} to {@link Recipient} type conversion
 */
@Component
public class RecipientDtoToRecipient implements Converter<RecipientDto, Recipient> {

    private RecipientService recipientService;

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
     * Converts the recipient dto object into a recipient model object
     *
     * @param recipientDto the recipient dto
     * @return the recipient object
     * @throws RecipientNotFoundException if recipient doesn't exist
     */
    @Override
    public Recipient convert(RecipientDto recipientDto) throws RecipientNotFoundException {

        Recipient recipient = (recipientDto.getId() != null ? recipientService.get(recipientDto.getId()) : new Recipient());

        recipient.setAccountNumber(recipientDto.getAccountNumber());
        recipient.setName(recipientDto.getName());
        recipient.setEmail(recipientDto.getEmail());
        recipient.setPhone(recipientDto.getPhone());
        recipient.setDescription(recipientDto.getDescription());

        return recipient;
    }
}
