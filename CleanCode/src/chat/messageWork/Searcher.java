package chat.messageWork;

import chat.Message;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Searcher{
    public Searcher(){

    }
    public void searchingByKeyword(BufferedReader consoleReader, List<Message> messages, FileWriter writer) {
        try {
            if (!messages.isEmpty()) {
                System.out.println("Enter the keyword:");
                String word = consoleReader.readLine();
                int amount = 0;
                Boolean found = false;
                for (Message it : messages)
                    if (it.getMessage().contains(word)) {
                        found = true;
                        amount++;
                        System.out.println(it.toString());
                    }
                if (!found)
                    System.out.println("No message contains \"" + word + "\"");

                writer.write(" Request: searching by keyword on " + new Timestamp(System.currentTimeMillis())
                        + "\n \t Amount of found messages  - " + amount + "\n");
                writer.flush();
            } else {
                System.out.println("Messages not found: chat history is empty.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void searchingByAuthor(BufferedReader consoleReader, List<Message> messages, FileWriter writer) {
        try {
            if (!messages.isEmpty()) {
                System.out.println("Enter the author's name:");
                String name = consoleReader.readLine();
                Boolean found = false;
                int amount = 0;
                for (Message message : messages)
                    if (message.getAuthor().equals(name)) {
                        found = true;
                        amount++;
                        System.out.println(message.toString());
                    }
                if (!found)
                    System.out.println("No message from user " + name + " found.");
                writer.write(" Request: searching by author on " + new Timestamp(System.currentTimeMillis())
                        + "\n \t Amount of deleted messages  - " + amount + "\n");
                writer.flush();
            } else {
                System.out.println("Messages not found: chat history is empty.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchRegularExpression(BufferedReader consoleReader, List<Message> messages, FileWriter writer) {
        try {
            if (!messages.isEmpty()) {
                int amount = 0;
                System.out.println("Enter a regular expression to find it between massages:");
                try {
                    Pattern p = Pattern.compile(consoleReader.readLine());

                    for (Message it : messages) {
                        Matcher m = p.matcher(it.getMessage());
                        if (m.find()) {
                            System.out.println(it.toString());
                            amount++;
                        }
                    }
                    if (amount == 0)
                        System.out.println("No message contains this regular expression.");
                    writer.write(" Request: searching by a regular expression on " + new Timestamp(System.currentTimeMillis())
                            + "\n \t Amount of found messages  - " + amount + "\n");
                    writer.flush();
                } catch (PatternSyntaxException e) {
                    System.out.println("Wrong input! " + e.getMessage());
                    writer.write(" Request: searching by a regular expression FAILED on " + new Timestamp(System.currentTimeMillis()) + "\n");
                    writer.flush();
                }
            } else
                System.out.println("Messages not found: chat history is empty.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void searchPeriodOfTime(BufferedReader consoleReader, List<Message> messages, FileWriter writer) {
        try {
            if (!messages.isEmpty()) {
                System.out.println("Enter the beginning of period: yyyy-MM-dd HH:mm:ss");
                int amount = 0;
                String dateBeg = consoleReader.readLine();
                System.out.println("Enter the end of period: yyyy-MM-dd HH:mm:ss");
                String dateEnd = consoleReader.readLine();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    Date dateB = formatter.parse(dateBeg);
                    Date dateE = formatter.parse(dateEnd);
                    Timestamp time1 = new Timestamp(dateB.getTime());
                    Timestamp time2 = new Timestamp(dateE.getTime());

                    if (time2.compareTo(time1) < 0) {
                        System.out.println("Wrong input.");
                        return;
                    }

                    for (Message it : messages)
                        if (it.getTime().compareTo(time1) >= 0 && it.getTime().compareTo(time2) <= 0) {
                            System.out.println(it.toString());
                            amount++;
                        }
                    writer.write(" Request: searching by a period of time on " + new Timestamp(System.currentTimeMillis())
                            + "\n \t Amount of found messages  - " + amount + "\n");
                    writer.flush();

                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                    writer.write(e.getMessage() + "on" + new Timestamp(System.currentTimeMillis()) + "\n");
                    writer.flush();
                }

            } else {
                System.out.println("Messages not found: chat history is empty.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}