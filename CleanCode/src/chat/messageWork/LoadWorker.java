package chat.messageWork;

import chat.Message;

import javax.json.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

public class LoadWorker{
    public void loadingFromFile(List<Message> messages, FileWriter writer){
        try {
            try {
                String jsonData = Files.readAllLines(Paths.get("chat.json")).toString();
                JsonReader jReader = Json.createReader(new StringReader(jsonData));
                JsonArray jArray = jReader.readArray();

                if (jArray.size() == 0) {
                    System.out.println("No messages to download: chat history is empty.");
                } else {
                    JsonArray arrMes = jArray.getJsonArray(0);
                    jReader.close();

                    for (int i = 0; i < arrMes.size(); i++) {
                        JsonObject newObj = arrMes.getJsonObject(i);
                        Timestamp time = new Timestamp(newObj.getJsonNumber("timestamp").longValue());
                        Message mes = new Message(newObj.getString("id"), newObj.getString("author"),
                                time, newObj.getString("message"));
                        messages.add(mes);
                    }
                    for (Message it : messages) {
                        System.out.println(it.toString());
                    }

                }
            } catch (JsonException e) {
                System.out.println(e.getMessage());
                writer.write(e.getMessage() + " on " + new Timestamp(System.currentTimeMillis()).toString() + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writingIntoFile(List<Message> messages) {
        try {
            if (!messages.isEmpty()) {
                File jfile = new File("chat.json");
                FileWriter fileWriter = new FileWriter(jfile);
                JsonWriter jsonWriter = Json.createWriter(fileWriter);
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                for (Message message : messages) {
                    arrayBuilder.add(
                            Json.createObjectBuilder()
                                    .add("id", message.getId())
                                    .add("author", message.getAuthor())
                                    .add("timestamp", message.getTime().getTime())
                                    .add("message", message.getMessage())
                                    .build());
                }
                JsonArray jsonArray = arrayBuilder.build();
                jsonWriter.writeArray(jsonArray);
                jsonWriter.close();
                System.out.println("Chat history has been recorded.");

            } else {
                System.out.println("No message to record: chat history is empty.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}