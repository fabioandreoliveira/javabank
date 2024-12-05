package io.codeforall.bootcamp.javabank.services;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiServiceImpl implements AiService {

    private ChatClient chatClient;

    @Autowired
    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public Generation joke() {
        Prompt prompt = new Prompt("Tell me a funny joke!");
        return chatClient.call(prompt).getResult();
    }
}
