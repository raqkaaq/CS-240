package Handler;

import Request.FillRequest;
import Result.FillResult;
import Server.Encode;
import Service.FillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Locale;

public class FillHandler implements HttpHandler {
    private FillResult res;
    private String[] args;
    private String message;
    private boolean success;
    private String generations;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        success = false;
        try{
            if(exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")){
                String url = exchange.getRequestURI().toString();
                args = HandlerPack.parseUrl(url);
                if(args.length <= 1 || args.length > 3){
                    message = "Invalid number of arguments";
                } else {
                    String user = args[1];
                    if(args.length == 3){
                        try {
                            generations = args[2];
                            success = true;
                        } catch (NumberFormatException e) {
                            message = "Invalid generations";
                            success = false;
                        }
                    }
                    if(success){
                        FillRequest req = new FillRequest(user, generations);
                        FillService fill = new FillService(req);
                        fill.fillRun();
                        res = fill.post();

                    }
                }
            }
            if(res.getSuccess()){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                HandlerPack.write(exchange, res);
            }else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                HandlerPack.write(exchange, res);
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            HandlerPack.write(exchange, res);
        }

    }
}
