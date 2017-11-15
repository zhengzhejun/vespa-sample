package com.yahoo.vespa.load;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class TransformData {

  public static void main (String[] args) throws Exception{
    String inputPath = "/Users/zhengzhejun/vespa-data/vespa/";
    String outputPath = "/Users/zhengzhejun/vespa-data/vespa-transform/";
    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath + "users")));
    PrintWriter writer = new PrintWriter(outputPath + "users");
    String idPrefix = "id:duokan";
    String str = "";
    while((str = reader.readLine()) != null) {
      JSONObject json = new JSONObject(str);
      String userId = json.optString("id", "");
      String age = json.optString("age", "");
      String sex = json.optString("sex", "");

      JSONObject outputJSON = new JSONObject();
      outputJSON.put("put", idPrefix + ":user::" + userId);
      JSONObject fields = new JSONObject();
      fields.put("user_id", userId);
      fields.put("age", age);
      fields.put("sex", sex);

      //tensor field
      JSONObject tensorJSON = new JSONObject();
      JSONArray cellsJSON = new JSONArray();
      for(int i = 0; i < 3; i++){
        JSONObject cellJSON = new JSONObject();
        JSONObject addressJSON = new JSONObject();
        addressJSON.put("x", "" + i);
        cellJSON.put("address", addressJSON);
        cellJSON.put("value", nextInt());
        cellsJSON.put(cellJSON);
      }
      tensorJSON.put("cells", cellsJSON);
      fields.put("user_vector", tensorJSON);

      outputJSON.put("fields", fields);
      writer.println(outputJSON.toString());
    }
    writer.flush();
    writer.close();
    reader.close();

    reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath + "items")));
    writer = new PrintWriter(outputPath + "items");
    while((str = reader.readLine()) != null) {
      JSONObject json = new JSONObject(str);
      String itemId = json.optString("id", "");
      String author = json.optString("author", "");
      String title = json.optString("title", "");

      JSONObject outputJSON = new JSONObject();
      outputJSON.put("put", idPrefix + ":item::" + itemId);
      JSONObject fields = new JSONObject();
      fields.put("item_id", itemId);
      fields.put("author", author);
      fields.put("title", title);

      //tensor field
      JSONObject tensorJSON = new JSONObject();
      JSONArray cellsJSON = new JSONArray();
      for(int i = 0; i < 3; i++){
        JSONObject cellJSON = new JSONObject();
        JSONObject addressJSON = new JSONObject();
        addressJSON.put("x", "" + i);
        cellJSON.put("address", addressJSON);
        cellJSON.put("value", nextInt());
        cellsJSON.put(cellJSON);
      }
      tensorJSON.put("cells", cellsJSON);
      fields.put("item_vector", tensorJSON);

      outputJSON.put("fields", fields);
      writer.println(outputJSON.toString());
    }
    writer.flush();
    writer.close();
    reader.close();

    reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath + "tags")));
    writer = new PrintWriter(outputPath + "tags");
    while((str = reader.readLine()) != null) {
      JSONObject json = new JSONObject(str);
      String tagId = json.optString("id", "");
      JSONObject fictionListJSON = json.optJSONObject("fiction_list");

      JSONObject outputJSON = new JSONObject();
      outputJSON.put("put", idPrefix + ":tag::" + tagId);
      JSONObject fields = new JSONObject();
      fields.put("tag_id", tagId);

      JSONArray fictionArray = new JSONArray();
      Iterator<?> iter = fictionListJSON.keys();
      while(iter.hasNext()) {
        String key = iter.next().toString();
        fictionArray.put(key);
      }
      fields.put("fiction_list", fictionArray);
      outputJSON.put("fields", fields);
      writer.println(outputJSON.toString());
    }
    writer.flush();
    writer.close();
    reader.close();
  }

  public static int nextInt() {
    return (int)(Math.random() * 10);
  }

}
