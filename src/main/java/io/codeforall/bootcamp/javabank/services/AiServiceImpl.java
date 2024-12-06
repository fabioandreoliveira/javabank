package io.codeforall.bootcamp.javabank.services;

import io.codeforall.bootcamp.javabank.functions.AccountInfoFunction;
import io.codeforall.bootcamp.javabank.functions.CustomerInfoFunction;
import io.codeforall.bootcamp.javabank.functions.RecipientInfoFunction;
import io.codeforall.bootcamp.javabank.persistence.VectorStore;
import io.codeforall.bootcamp.javabank.persistence.model.Customer;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {

    private ChatClient chatClient;

    @Value("${ai.rag_prompt_template}")
    private Resource ragPromptTemplate;

    @Value("${ai.function_prompt_template}")
    private Resource functionPromptTemplate;


    private VectorStore vectorStore;

    @Autowired
    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Autowired
    public void setVectorStore(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public Generation joke() {
        Prompt prompt = new Prompt("Tell me a funny joke!");
        return chatClient.call(prompt).getResult();
    }

    @Override
    public Generation info(String question) {

        List<String> contentList = vectorStore.search(question);

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Prompt prompt = promptTemplate
                .create(Map
                        .of("input", question,
                                "documents", String.join("\n", contentList)
                        )
                );

        return chatClient.call(prompt).getResult();
    }

    @Override
    public Generation customerInfo(Customer customer, String question) {
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(List.of(
                        FunctionCallbackWrapper.builder(new CustomerInfoFunction(customer))
                                .withName("CustomerInfo")
                                .withDescription("Get personal details for a customer, such as email, address or phone number and information on the number of accounts and total balance")
                                .build(),
                        FunctionCallbackWrapper.builder(new AccountInfoFunction(customer))
                                .withName("AccountInfo")
                                .withDescription("Get information regarding a list of customer accounts such as individual account types and balance")
                                .build(),
                        FunctionCallbackWrapper.builder(new RecipientInfoFunction(customer))
                                .withName("RecipientInfo")
                                .withDescription("Get information regarding a list of recipients for a customer")
                                .build()
                )).build();

        Message userMessage = new PromptTemplate(question).createMessage();
        Message systemMessage = new SystemPromptTemplate(functionPromptTemplate).createMessage();

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage), chatOptions);

        return chatClient.call(prompt).getResult();
    }
}
