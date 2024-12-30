package alom.model;

public class ChannelMessage {
    private String sender;
    private String channel;
    private String content;

    // Constructeurs
    public ChannelMessage() {}
    
    public ChannelMessage(String sender, String channel, String content) {
        this.sender = sender;
        this.channel = channel;
        this.content = content;
    }

    // Getters & Setters
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
