package chat;
import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        ChatStore storage = new ChatStore();
        storage.startChat();
    }
}