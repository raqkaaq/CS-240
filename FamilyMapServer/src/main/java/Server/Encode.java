package Server;

import Request.*;
import Result.*;
import com.google.gson.Gson;

public class Encode {
    public static String encode(ClearResult res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(EventIDResult res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(EventResult res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(FillResult res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(LoadResult res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(LoginResult res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(PersonIDResult res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(PersonResult res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(RegisterResult res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(Result res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(EventIDRequest res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(FillRequest res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(LoadRequest res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(LoginRequest res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(PersonIDRequest res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
    public static String encode(RegisterRequest res){
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return json;
    }
}
