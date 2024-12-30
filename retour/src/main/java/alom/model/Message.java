package alom.model;

public class Message {
    private String sender;
    private String content;
    private String recipient; // peut être un utilisateur ou un canal

    public Message(String sender, String content, String recipient) {
        this.sender = sender;
        this.content = content;
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getRecipient() {
        return recipient;
    }
}
