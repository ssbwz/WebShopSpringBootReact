package fontys.sem3.individualProject.business;


import fontys.sem3.individualProject.domain.Reply;
import fontys.sem3.individualProject.domain.SendMessageRequest;

public interface ChatService {
    Reply reply(SendMessageRequest request);
}
