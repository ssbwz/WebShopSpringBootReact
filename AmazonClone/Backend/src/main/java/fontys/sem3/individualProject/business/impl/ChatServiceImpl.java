package fontys.sem3.individualProject.business.impl;

import fontys.sem3.individualProject.business.ChatService;
import fontys.sem3.individualProject.domain.Reply;
import fontys.sem3.individualProject.domain.SendMessageRequest;
import fontys.sem3.individualProject.domain.UserType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ChatServiceImpl implements ChatService {
    private static long replyId = 0;
    @Override
    public Reply reply(SendMessageRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        replyId++;

        Reply reply = Reply.builder().id(replyId).dateTime(formatDateTime).userType(UserType.Owner).build();
        switch (request.getQuestion()){
            case  "How to contact us?": reply.setContent("You can contact us via s.bawazier@student.fontys.nl");break;
            default: reply.setContent("We don't have an answer for this question");
        }
        return reply;
    }
}
