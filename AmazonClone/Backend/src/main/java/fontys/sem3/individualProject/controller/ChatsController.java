package fontys.sem3.individualProject.controller;

import fontys.sem3.individualProject.business.ChatService;
import fontys.sem3.individualProject.domain.Reply;
import fontys.sem3.individualProject.domain.SendMessageRequest;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatsController {

    private ChatService chatService;

    @MessageMapping("/chatMessages")
    @SendTo("/chat")
    public Reply chatting(SendMessageRequest request) throws Exception {
        return chatService.reply(request);
    }
}
