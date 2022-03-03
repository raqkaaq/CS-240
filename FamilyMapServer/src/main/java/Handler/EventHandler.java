package Handler;

import Request.EventIDRequest;
import Result.EventIDResult;
import Result.EventResult;
import Result.Result;
import Service.EventIDService;
import Service.EventService;
import com.sun.jdi.request.EventRequest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Locale;

public class EventHandler implements HttpHandler {
    private EventResult res;
    private EventIDResult res1;
    private String[] args;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("get")) {
                if (exchange.getRequestHeaders().containsKey("Authorization")) {
                    String auth = exchange.getRequestHeaders().getFirst("Authorization");
                    String url = exchange.getRequestURI().toString();
                    args = HandlerPack.parseUrl(url);
                    if (args.length != 2 && args.length != 1) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        Result res = new Result("Invalid number of parameters", false);
                        HandlerPack.write(exchange, res);
                    } else if (args.length == 2) {
                        EventIDRequest req = new EventIDRequest(args[1], auth);
                        EventIDService serv = new EventIDService(req);
                        res1 = serv.post();
                        if (res1.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            HandlerPack.write(exchange, res1);
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            HandlerPack.write(exchange, res1);
                        }
                    } else {
                        EventService serv = new EventService(auth);
                        res = serv.post();
                        if (res.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            HandlerPack.write(exchange, res);
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            HandlerPack.write(exchange, res);
                        }
                    }
                }
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            Result res = new Result("Internal server error", false);
            HandlerPack.write(exchange, res);
        }
    }
}
