package chat;

import chat.messageWork.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.server.UID;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatStore {
    private Searcher search;
    private LoadWorker loadWorker;
    public ChatStore() throws IOException {
        search = new Searcher();
        loadWorker = new LoadWorker();
    }
    public void startChat(){
        try {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            List<Message> messages = new ArrayList<>();
            FileWriter writer = new FileWriter("logfile.txt", true);
            writer.write("Usage history: ");
            String choice = "";

            System.out.println("Please, select what you like to do:");
            System.out.println("1) Download messages from file.");
            System.out.println("2) Record chat history into file.");
            System.out.println("3) Add new message.");
            System.out.println("4) Show chronological history.");
            System.out.println("5) Delete the message by ID.");
            System.out.println("6) Search for messages by author.");
            System.out.println("7) Search messages by keyword.");
            System.out.println("8) Search messages by regular expression.");
            System.out.println("9) View the history of messages over a period of time.");
            System.out.println("10) Exit.");

            switcher(consoleReader, messages, writer, choice);
            writer.close();
            consoleReader.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void switcher(BufferedReader consoleReader, List<Message> messages, FileWriter writer, String choice) {
        try {

            while (!choice.equals("10")) {
                System.out.println("Please, select what you like to do:");
                choice = consoleReader.readLine();
                switch (choice) {
                    case "1": {
                        loadWorker.loadingFromFile(messages, writer);
                        break;
                    }
                    case "2": {
                        loadWorker.writingIntoFile(messages);
                        break;
                    }
                    case "3": {
                        newMessage(consoleReader, messages, writer);
                        break;
                    }
                    case "4": {
                        if (!messages.isEmpty()) {
                            Collections.sort(messages);
                            for (Message it : messages)
                                System.out.println(it.toString());
                        } else {
                            System.out.println("Chat history is empty.");
                        }
                        break;
                    }
                    case "5": {
                        deleting(consoleReader, messages, writer);
                        break;
                    }
                    case "6": {
                        search.searchingByAuthor(consoleReader, messages, writer);
                        break;
                    }
                    case "7": {
                        search.searchingByKeyword(consoleReader, messages, writer);
                        break;
                    }
                    case "8": {
                        search.searchRegularExpression(consoleReader, messages, writer);
                        break;
                    }
                    case "9": {
                        search.searchPeriodOfTime(consoleReader, messages, writer);
                        break;
                    }
                    case "10": {
                        System.out.println("Good bye!");
                        System.exit(0);
                        break;
                    }
                    default: {
                        System.out.println("Error: wrong input! Try again.");
                        writer.write("Error: wrong input!" + "\t" + new Timestamp(System.currentTimeMillis()) + "\n");
                        writer.flush();
                        break;
                    }
                }
            }
        } catch(IOException e){
                System.out.println(e.getMessage());
        }
    }
    public void deleting(BufferedReader consoleReader, List<Message> messages, FileWriter writer) {
        try {
            if (!messages.isEmpty()) {
                System.out.println("Enter an id of message you want to delete:");
                String index = consoleReader.readLine();
                int size = messages.size();
                for (int i = 0; i < messages.size(); i++)
                    if (messages.get(i).getId().equals(index)) {
                        messages.remove(i);
                        System.out.println("Successfully deleted!");
                        break;
                    }
                if (size == messages.size())
                    System.out.println("No message to delete: ID not found.");
                writer.write(" Request: deleting on " + new Timestamp(System.currentTimeMillis())
                        + "\n \t Amount of deleted messages  - " + (size - messages.size()) + "\n");
                writer.flush();
            } else {
                System.out.println("No message to delete: chat history is empty.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void newMessage(BufferedReader consoleReader, List<Message> messages, FileWriter writer) {
        try {
            System.out.println("Enter your name:");
            String userName = consoleReader.readLine();
            System.out.println("Enter your message:");
            String userMes = consoleReader.readLine();

            if (userMes.length() > 140) {
                System.out.println("Message is too large (more than 140 letters).");
                writer.write("Warning! Message is too large (more than 140 letters) on "
                        + new Timestamp(System.currentTimeMillis()).toString() + "\n");
                writer.flush();
                return;
            }
            Message tempMes = new Message(new UID().toString(), userName, new Timestamp(System.currentTimeMillis()), userMes);
            messages.add(tempMes);
            System.out.println(tempMes.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}