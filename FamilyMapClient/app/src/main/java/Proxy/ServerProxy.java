package Proxy;
import static Proxy.ProxyPack.readString;
import static Proxy.ProxyPack.writeString;

import android.widget.Toast;

import Model.Event;
import Request.*;
import Result.*;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ServerProxy {
    /*
    login
    register
    getpeople
    getevents

    clear
    fill
    getperson
    getevents
    load
     */
    public String post(URL url, String reqString) throws IOException {
        String responseData;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.addRequestProperty("Accept", "application/json");
        connection.connect();
        OutputStream os = connection.getOutputStream();
        writeString(reqString, os);
        os.close();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream response = connection.getInputStream();
            responseData = readString(response);
            System.out.println(responseData);
        } else {
            System.out.println("Error: " + connection.getResponseMessage());
            InputStream response = connection.getErrorStream();
            responseData = readString(response);
            System.out.println(responseData);
        }
        return responseData;
    }
    public String get(URL url) throws IOException {
        String responseData;
        DataCache data = DataCache.getInstance();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(false);
        connection.addRequestProperty("Authorization", data.getAuth().getAuthToken());
        connection.addRequestProperty("Accept","application/json");
        connection.connect();
        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream response = connection.getInputStream();
            responseData = readString(response);
            System.out.println(responseData);
        } else {
            System.out.println("Error: " + connection.getResponseMessage());
            InputStream response = connection.getErrorStream();
            responseData = readString(response);
            System.out.println(responseData);
        }
        return responseData;
    }


    public LoginResult login (LoginRequest req, String hostname, String port) {
        LoginResult result = null;
        try {
            Gson gson = new Gson();
            String request = gson.toJson(req);
            URL url = new URL("http://" + hostname + ":" + port + "/user/login");
            String json = post(url, request);
            result = gson.fromJson(json, LoginResult.class);
        } catch (IOException e){
            result = new LoginResult("Unable to connect to Server");
            e.printStackTrace();
        }
        return result;
    }
    public RegisterResult register(RegisterRequest req, String hostname, String port){
        RegisterResult result = null;
        try{
            Gson gson = new Gson();
            String request = gson.toJson(req);
            URL url = new URL("http://" + hostname + ":" + port + "/user/register");
            String json = post(url, request);
            result = gson.fromJson(json, RegisterResult.class);
        } catch (IOException e){
            result = new RegisterResult("Unable to connect to Server");
            e.printStackTrace();
        }
        return result;
    }
    public PersonResult getPeople(String hostname, String port){
        PersonResult result = null;
        try{
            Gson gson = new Gson();
            URL url = new URL("http://" + hostname + ":" + port + "/person");
            String json = get(url);
            result = gson.fromJson(json, PersonResult.class);
        } catch (IOException e) {
            result = new PersonResult("Unable to connect to the Server");
            e.printStackTrace();
        }
        return result;
    }
    public EventResult getEvents(String hostname, String port){
        EventResult result = null;
        try{
            Gson gson = new Gson();
            URL url = new URL("http://" + hostname + ":" + port + "/event");
            String json = get(url);
            result = gson.fromJson(json, EventResult.class);
        } catch (IOException e) {
            result = new EventResult("Unable to connect to the Server");
            e.printStackTrace();
        }
        return result;
    }

}
