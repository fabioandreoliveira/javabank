package io.codeforall.bootcamp.javabank.services.mock;

import io.codeforall.bootcamp.javabank.persistence.model.Recipient;
import io.codeforall.bootcamp.javabank.services.RecipientService;

/**
 * A mock {@link RecipientService} implementation
 */
public class MockRecipientService extends AbstractMockService<Recipient> implements RecipientService {

    /**
     * @see RecipientService#get(Integer)
     */
    @Override
    public Recipient get(Integer id) {

        return modelMap.get(id);
    }
}
