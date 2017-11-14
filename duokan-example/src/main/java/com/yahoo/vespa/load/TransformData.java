package com.yahoo.vespa.load;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
      String fictionList = json.optString("fictionList", "");

      JSONObject outputJSON = new JSONObject();
      outputJSON.put("put", idPrefix + ":tag::" + tagId);
      JSONObject fields = new JSONObject();
      fields.put("tag_id", tagId);
      fields.put("fiction_list", fictionList);
      outputJSON.put("fields", fields);
      writer.println(outputJSON.toString());
    }
    writer.flush();
    writer.close();
    reader.close();
  }

}
