package io.codeforall.bootcamp.javabank.services;

import io.codeforall.bootcamp.javabank.exceptions.RecipientNotFoundException;
import io.codeforall.bootcamp.javabank.persistence.model.Recipient;

/**
 * Common interface for recipient services, provides a method to get the recipient
 */
public interface RecipientService {

    /**
     * Gets the recipient
     *
     * @param id the recipient id
     * @return the recipient
     * @throws RecipientNotFoundException if recipient doesn't exist
     */
    Recipient get(Integer id) throws RecipientNotFoundException;
}
