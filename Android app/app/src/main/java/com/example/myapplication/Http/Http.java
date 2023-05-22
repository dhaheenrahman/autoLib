package com.example.myapplication.Http;
import android.os.Environment;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Http{

    URL url;
    Http(String link){
        try{
            url=new URL(link);
        }
        catch(Exception e){e.printStackTrace();}
    }
    public Map<String,Object> get() throws Exception{
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        con.disconnect();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response.toString());
            return ((Map<String, Object>) jsonObject);
        }
        catch(Exception e) {  System.out.println(response.toString());}

        return null;
    }
    public Map<String,Object> post(String jsonString) throws Exception{

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] requestBodyBytes = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(requestBodyBytes, 0, requestBodyBytes.length);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder responseBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        reader.close();
        connection.disconnect();
        String response = responseBuilder.toString();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(response);
            return ((Map<String, Object>) jsonObject);
        }
        catch(Exception e) {e.printStackTrace();}
        return null;
    }

    public static void printMap(Map <String,Object> map){
        if(map==null) System.out.println("NULL\n A null map was provided for printing");
        else{
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                System.out.println(key + ": " + value);
            }
        }

    }
    public boolean download(String location){
        try {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            InputStream inputStream = connection.getInputStream();
            String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
            File downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            FileOutputStream outputStream = new FileOutputStream(new File(downloadsFolder, fileName));

            byte[] buffer = new byte[1024];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();
            connection.disconnect();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }
}


