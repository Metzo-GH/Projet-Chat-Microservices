package alom.model;

public class ChannelAction {
    private String username;
    private String channel;

    // Constructeurs
    public ChannelAction() {}
    
    public ChannelAction(String username, String channel) {
        this.username = username;
        this.channel = channel;
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
}
