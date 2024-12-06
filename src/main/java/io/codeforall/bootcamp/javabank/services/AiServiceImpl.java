package io.codeforall.bootcamp.javabank.services;

import io.codeforall.bootcamp.javabank.persistence.VectorStore;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {

    private ChatClient chatClient;

    @Value("${ai.rag_prompt_template}")
    private Resource ragPromptTemplate;

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
        Map<String, Object> shit = new HashMap<>();
        shit.put("input", question);
        shit.put("documents", String.join("\n", contentList));

        Prompt prompt = promptTemplate.create(shit);

        return chatClient.call(prompt).getResult();
    }
}
