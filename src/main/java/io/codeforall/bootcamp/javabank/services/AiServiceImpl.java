package io.codeforall.bootcamp.javabank.services;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {


    private ChatClient chatClient;
    private Rag rag;

    @Autowired
    public void setRag(Rag rag) {
        this.rag = rag;
    }

    @Autowired
    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public Generation joke() {
        Prompt prompt = new Prompt("Tell me a funny joke!");
        return chatClient.call(prompt).getResult();
    }

    public Generation ragRequest(String question) {

        List<String> contentList = rag.search(question);

        Resource ragPromptTemplate = new ClassPathResource("/ai/templates/rag.st");
        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);

        Map<String, Object> model = Map.of(
                "input", question,
                "documents", String.join("\n", contentList)
        );

        Prompt prompt = promptTemplate.create(model);
        return chatClient.call(prompt).getResult();
    }

}

