package Server;

import Request.*;
import Result.*;
import com.google.gson.Gson;

import java.io.Reader;

//contains functions useful to decode json requests, only load, login and register are post requests, but I wanted to make the others just in case I wanted to add functionality
public class Decode {
    public static EventIDRequest decodeEventIDRequest(Reader json){
        EventIDRequest ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, EventIDRequest.class);
        return ret;
    }
    public static FillRequest decodeFillRequest(Reader json){
        FillRequest ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, FillRequest.class);
        return ret;
    }
    public static LoadRequest decodeLoadRequest(Reader json){
        LoadRequest ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, LoadRequest.class);
        return ret;
    }
    public static LoginRequest decodeLoginRequest(Reader json){
        LoginRequest ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, LoginRequest.class);
        return ret;
    }
    public static PersonIDRequest decodePersonIDRequest(Reader json){
        PersonIDRequest ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, PersonIDRequest.class);
        return ret;
    }
    public static RegisterRequest decodeRegisterRequest(Reader json){
        RegisterRequest ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, RegisterRequest.class);
        return ret;
    }

    public static ClearResult decodeClearResult(Reader json){
        ClearResult ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, ClearResult.class);
        return ret;
    }
    public static EventIDResult decodeEventIDResult(Reader json){
        EventIDResult ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, EventIDResult.class);
        return ret;
    }
    public static EventResult decodeEventResult(Reader json){
        EventResult ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, EventResult.class);
        return ret;
    }
    public static FillResult decodeFillResult(Reader json){
        FillResult ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, FillResult.class);
        return ret;
    }
    public static LoadResult decodeLoadResult(Reader json){
        LoadResult ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, LoadResult.class);
        return ret;
    }
    public static LoginResult decodeLoginResult(Reader json){
        LoginResult ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, LoginResult.class);
        return ret;
    }
    public static PersonIDResult decodePersonIDResult(Reader json){
        PersonIDResult ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, PersonIDResult.class);
        return ret;
    }
    public static PersonResult decodePersonResult(Reader json){
        PersonResult ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, PersonResult.class);
        return ret;
    }
    public static RegisterResult decodeRegisterResult(Reader json){
        RegisterResult ret;
        Gson gson = new Gson();
        ret = gson.fromJson(json, RegisterResult.class);
        return ret;
    }
}
