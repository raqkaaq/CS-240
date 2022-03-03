package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

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
                if(url.length() == 1){
                    String path = new String("web/index.html");
                    Path file = FileSystems.getDefault().getPath(path);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Files.copy(file, exchange.getResponseBody());
                    exchange.getResponseBody().close();
                } else {
                    String path = new String("web" + url);
                    Path file = FileSystems.getDefault().getPath(path);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Files.copy(file, exchange.getResponseBody());
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
