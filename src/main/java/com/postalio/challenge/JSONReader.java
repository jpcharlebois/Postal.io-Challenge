package com.postalio.challenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.net.ssl.HttpsURLConnection;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONReader {
  private final static String TOKEN = "Cincinnati";
  
  /**
   * readFromUrl taken in a string URL
   * GET request response JsonObject
   * Parse JsonObject count occurances
   * 
   * Returns JsonObject
   **/
  public static int readFromUrlandCount(String sURL, String token) throws IOException {
    // Connect to the URL using java's native library
    URL url = new URL(sURL);
    HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
    conn.setRequestMethod("GET");
    conn.connect();
    InputStream is = (InputStream) conn.getContent();
    try {
      // Convert to a JSON object to print data
      JsonElement root = JsonParser.parseReader(new InputStreamReader(is)); //Convert the input stream to a json element
      JsonObject rootObj = root.getAsJsonObject(); //May be an array, may be an object. 
      JsonElement parse = rootObj.get("parse");
      JsonObject parseObj = parse.getAsJsonObject();
      JsonElement text = parseObj.get("text");
      JsonObject textObj = text.getAsJsonObject();
      
      conn.disconnect();
      return countWord(textObj, token);
    }
    finally {
      is.close();
    }
  }
  
  /** 
   * countWord takes in a JasonObject and a string 
   * searches the JsonObject for occurances of string
   * counts occurances
   * returns count 
   * 
   */
  public static int countWord(JsonObject textObj, String token) {
    Pattern SEARCH_PATTERN = Pattern.compile(token);
    Matcher countMatcher = SEARCH_PATTERN.matcher(textObj.toString());
    
    int count = 0;
    while (countMatcher.find()) {
      count++;
    }
    return count;
  }
  
  /**
   * readFromUrl taken in a string URL
   * GET request response JsonObject
   * Parse JsonObject count occurances
   * 
   * Returns JsonObject
   **/
  public static void readFromUrl(String sURL) throws IOException {
    // Connect to the URL using java's native library
    URL url = new URL(sURL);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    InputStream in = connection.getInputStream();
    ZipInputStream zipIn = new ZipInputStream(in);
    ZipEntry entry = zipIn.getNextEntry();
    while(entry != null) {
        System.out.println(entry.getName());
        if (!entry.isDirectory()) {
            // if the entry is a file, extracts it type
            // if csv parse for ip

        } else {

        }
        zipIn.closeEntry();
        entry = zipIn.getNextEntry();
    }
  }
  

  public static void main(String[] args) throws IOException {
      String url = "https://en.wikipedia.org/w/api.php?action=parse&section=0&prop=text&format=json&page=Cincinnati";
      int count = readFromUrlandCount(url, TOKEN);

      System.out.println(count);
  }
}
