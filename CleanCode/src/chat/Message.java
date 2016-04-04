package chat;
import java.sql.Timestamp;

public class Message implements Comparable<Message>{
    private String id;
    private String message;
    private Timestamp timestamp;
    private String author;

    public Message(String id, String author, Timestamp time, String message) {
        this.id = id;
        this.author = author;
        this.message = message;
        this.timestamp = time;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTime() {
        return timestamp;
    }

    public void setTime(Timestamp time) {
        this.timestamp = time;
    }
    @Override
    public String toString() {
        return "{" +
                "id:\"" + id + '\"' +
                ",author:\"" + author + '\"' +
                ",message:\"" + message + '\"' +
                ",timestamp:\"" + timestamp + '\"'+
                '}';
    }
    @Override
    public int compareTo(Message o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}
