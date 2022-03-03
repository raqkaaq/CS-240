package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class DefaultFileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            if(exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("get")){
                String url = exchange.getRequestURI().toString();
                if(url.length() == 1){ //for the / call, returns index.html data and sends it to the client
                    String path = new String("web/index.html");
                    Path file = FileSystems.getDefault().getPath(path);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Files.copy(file, exchange.getResponseBody());
                    exchange.getResponseBody().close();
                } else { //Attempts to find the /<input> file, if it can't find it, return the 404.html file
                    String path = new String("web" + url);
                    Path file = FileSystems.getDefault().getPath(path);
                    File testing = new File(file.toString());
                    if(testing.exists()) { //check if the given file exists in the server
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        Files.copy(file, exchange.getResponseBody()); //I personally don't like this setup as it seems like a security risk, but since there is nothing in the web folder I guess its okay lol
                    } else { //if no file was found, return the 404.html
                        file = FileSystems.getDefault().getPath("web/HTML/404.html");
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                        Files.copy(file, exchange.getResponseBody());

                    }
                    exchange.getResponseBody().close();
                }
                success = true;
            }
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
