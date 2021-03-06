package Handler;

import Result.ClearResult;
import Service.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Locale;
//Handles /clear calls
public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            if(exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")){
                ClearService clear = new ClearService();
                ClearResult res = clear.clearTables(); //if no error is thrown, the clear was successful
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                HandlerPack.write(exchange, res);
                success = true;
            }
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            //e.printStackTrace();
        }
    }
}
