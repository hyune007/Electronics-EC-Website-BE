package com.hyu.electronicsecwebsitebe.controller;

import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChatAIController {

    private final AzureOpenAiChatModel chatModel;

    @Autowired
    public ChatAIController(AzureOpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/api/ai/generate")
    public Map<String, String> generate(@RequestParam("message") String message) {

        SystemMessage systemMessage = new SystemMessage("""
        Bạn là một trợ lý AI chuyên về tư vấn thiết bị điện tử.
        - Trả lời ngắn gọn, dễ hiểu
        - Luôn thân thiện
        - Nếu không biết thì nói không biết
    """);

        UserMessage userMessage = new UserMessage(message);

        Prompt prompt = new Prompt(systemMessage, userMessage);

        ChatResponse response = chatModel.call(prompt);

        String content = response.getResult().getOutput().getText();

        return Map.of("generation", content);
    }
}
