package io.codeforall.bootcamp.javabank.services;

import org.springframework.ai.chat.Generation;

import javax.imageio.ImageWriter;

public interface AiService {

    Generation joke();

    Generation ragRequest(String question);
}
