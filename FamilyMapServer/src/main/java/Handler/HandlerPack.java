package Handler;

import Result.Result;
import Server.Encode;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class HandlerPack {
    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(str);
        osw.flush();
    }
    public static void write(HttpExchange exchange, Result res) throws IOException {
        String json = Encode.encode(res);
        OutputStream os = exchange.getResponseBody();
        HandlerPack.writeString(json, os);
        os.close();
    }
    public static String[] parseUrl(String url){
        StringBuilder sb = new StringBuilder(url);
        sb.deleteCharAt(0);
        return sb.toString().split("/");
    }
}
