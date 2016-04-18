package by.bsu.up.chat.common.models;

import javax.json.JsonObject;
import java.io.Serializable;

public class Message implements Serializable {


    private String id;
    private String username;
    private long timestamp;
    private String text;
    private Boolean edited;
    private Boolean deleted;

    public Message() {
        this.id = "";
        this.username = "";
        this.timestamp = 0;
        this.text = "";
        this.edited = false;
        this.deleted = false;
    }

    public Message(JsonObject temp) {
        this.username = temp.getString("username");
        this.timestamp = temp.getJsonNumber("timestamp").longValue();
        this.text = temp.getString("message");
        this.edited = temp.getBoolean("edited");
        this.deleted = temp.getBoolean("deleted");
        this.id = temp.getString("id");
    }

    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
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

    public Boolean getDeleted() {
       return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


}