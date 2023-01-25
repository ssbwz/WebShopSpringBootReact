package fontys.sem3.individualProject.business.impl;

import fontys.sem3.individualProject.domain.Reply;
import fontys.sem3.individualProject.domain.SendMessageRequest;
import fontys.sem3.individualProject.domain.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {

    @InjectMocks
    private ChatServiceImpl chatService;

    @Test
    public void reply_WhenThereIsAnAnswer_ReturnAnAnswer() {
        //Arrange
        SendMessageRequest request = SendMessageRequest.builder().question("How to contact us?").build();
        //Act
        Reply reply = chatService.reply(request);
        //Assert
        assertEquals("You can contact us via s.bawazier@student.fontys.nl", reply.getContent(),"It doesn't return an existed answer");
    }

    @Test
    public void reply_WhenThereIsNotAnAnswer_ReturnUnknownQuestionReply() {
        //Arrange
        SendMessageRequest request = SendMessageRequest.builder().question("What is your favorite color?").build();
        //Act
        Reply reply = chatService.reply(request);
        //Assert
        assertEquals("We don't have an answer for this question", reply.getContent(),"it doesn't return the We don't have an answer for this question when it doesn't find an answer");
    }
}
