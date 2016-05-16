package main.java.models;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AccountStorage {
    private ArrayList<Account> accounts = new ArrayList<Account>();

    public void loadAccount() {
        try {
            JsonArray jArray = getJson();

            if(!jArray.isEmpty()){
                JsonArray arrayAcc = jArray.getJsonArray(0);
                accounts.clear();
                for(int i =0; i<arrayAcc.size(); i++){
                    JsonObject jObj = arrayAcc.getJsonObject(i);
                    accounts.add(new Account(jObj));
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean isExist(String login, String pass){
        for(Account acc: accounts){
            if(acc.getUsername().equals(login) && acc.getPassword().equals(pass))
                return true;
        }
        return false;
    }
    public static JsonArray getJson() throws IOException {

        List<String> list = null;
        list = Files.readAllLines(Paths.get("C:\\Users\\user\\IdeaProjects\\UP\\webchatapp\\login.json"));

        String jData = list.toString();

        JsonReader jsonReader = Json.createReader(new StringReader(jData));
        JsonArray jsonArray = jsonReader.readArray();

        jsonReader.close();
        return jsonArray;
    }
}
