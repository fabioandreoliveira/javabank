package io.codeforall.bootcamp.javabank.services;

import io.codeforall.bootcamp.javabank.persistence.model.Customer;
import org.springframework.ai.chat.Generation;

public interface AiService {

    Generation joke();

    Generation info(String question);

    Generation customerInfo(Customer customer, String question);
}
