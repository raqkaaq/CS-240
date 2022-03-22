package Handler;

import Result.Result;
import Server.Encode;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class HandlerPack {
    //generic function to write a string to and OutputStream
    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(str);
        osw.flush();
    }
    //Generic function to encode a Result as json, and write it to the exchange responseBody OutputStream
    public static void write(HttpExchange exchange, Result res) throws IOException {
        String json = Encode.encode(res);
        OutputStream os = exchange.getResponseBody();
        HandlerPack.writeString(json, os);
        os.close();
    }
    //Function that takes a url of the form /something/else/here and splits it into an array
    public static String[] parseUrl(String url){
        StringBuilder sb = new StringBuilder(url);
        sb.deleteCharAt(0);
        return sb.toString().split("/");
    }
}
