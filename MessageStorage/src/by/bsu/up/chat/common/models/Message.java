package by.bsu.up.chat.common.models;

import javax.json.JsonObject;
import java.io.Serializable;

public class Message implements Serializable {

    private String id;
    private String username;
    private long timestamp;
    private String text;
    private String edited;
    private String deleted;

    public Message() {
        this.id = "";
        this.username = "";
        this.timestamp = 0;
        this.text = "";
        this.edited = "";
        this.deleted = "";
    }

    public Message(JsonObject temp) {
        this.username = temp.getString("username");
        this.timestamp = temp.getJsonNumber("timestamp").longValue();
        this.text = temp.getString("message");
        this.edited = temp.getString("edited");
        this.deleted = temp.getString("deleted");
        this.id = temp.getString("id");
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Message{" +
                "username:'" + username + '\'' +
                ", text:'" + text + '\'' +
                ", id:'" + id + '\'' +
                ", timestamp:" + timestamp +
                ", edited:'" + edited + '\'' +
                ", deleted:'" + edited + '\'' +
                '}';
    }
}