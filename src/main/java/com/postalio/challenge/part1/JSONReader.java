package com.postalio.challenge.part1;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONReader {
  private final static String TOKEN = "Cincinnati";
  
 
  /** 
   * GET request response JsonObject
   * 
   * 
   * Returns JsonObject
   * @throws IOException
   **/
  private JsonObject GETfromURL(String URL) throws IOException {
    // Connect to the URL using java's native library
    URL url = new URL(URL);
    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    conn.setRequestMethod("GET");
    conn.connect();
    InputStream is = (InputStream) conn.getContent();

    // form Json Object
    JsonElement root;
    try {
      // Convert to a JSON object to print data
      root = JsonParser.parseReader(new InputStreamReader(is)); //Convert the input stream to a json element
    }
    finally {
      is.close();
      conn.disconnect();
    }
    return root.getAsJsonObject();
  }

 /**
   * readFromUrl taken in a string URL
   * Parse JsonObject text
   * 
   **/
  public int readFromUrlandCount(String url, String token) throws IOException {
    JsonObject rootObj = GETfromURL(url);

    JsonElement parse = rootObj.get("parse");
    JsonObject parseObj = parse.getAsJsonObject();
    JsonElement text = parseObj.get("text");
    JsonObject textObj = text.getAsJsonObject();
      
    return tokenCounter(textObj, token);

  }
  
  /** 
   * countWord takes in a JasonObject and a string 
   * searches the JsonObject for occurances of string
   * counts occurances
   * returns count 
   */
  public static int tokenCounter(JsonObject textObj, String token) {
    Pattern SEARCH_PATTERN = Pattern.compile(token);
    Matcher countMatcher = SEARCH_PATTERN.matcher(textObj.toString());
    
    int count = 0;
    while (countMatcher.find()) {
      count++;
    }
    return count;
  }

  public static void main(String[] args) throws IOException {
      JSONReader jReader = new JSONReader();
      jReader.runPart1();
  }

  public void runPart1() throws IOException {
    String url = "https://en.wikipedia.org/w/api.php?action=parse&section=0&prop=text&format=json&page=Cincinnati";
    int count = readFromUrlandCount(url, TOKEN); 
    System.out.println(count); // 176
  }
}
