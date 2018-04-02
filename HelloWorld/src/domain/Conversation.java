package domain;

import java.util.List;

public class Conversation {
    private int id;
    private List<Message> messages;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }


}
