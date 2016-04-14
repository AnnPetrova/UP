package by.bsu.up.chat.common.models;

import javax.json.JsonObject;
import java.io.Serializable;

public class Message implements Serializable {

    private String id;
    private String author;
    private long timestamp;
    private String text;
    private String edited;

    public Message() {
        this.id = "";
        this.author = "";
        this.timestamp = 0;
        this.text = "";
        this.edited = "";
    }

    public Message(JsonObject temp) {
        this.author = temp.getString("author");
        this.timestamp = temp.getJsonNumber("timestamp").longValue();
        this.text = temp.getString("message");
        this.edited = temp.getString("edited");
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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


    @Override
    public String toString() {
        return "Message{" +
                "author='" + author + '\'' +
                ", id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", text='" + text + '\'' +
                ", edited='" + edited + '\'' +
                '}';
    }
}