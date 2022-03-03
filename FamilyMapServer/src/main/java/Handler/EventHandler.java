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
                    args = HandlerPack.parseUrl(url); //Uses the parseUrl to parse the string into an array
                    if (args.length != 2 && args.length != 1) { //if the args length isnt 1 or 2 then there are too many parameters bad request
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        Result res = new Result("Invalid number of parameters", false);
                        HandlerPack.write(exchange, res);
                    } else if (args.length == 2) { //If there are 2 args, then one is the 'event' and the other is automatically considered an eventID so
                        EventIDRequest req = new EventIDRequest(args[1], auth);
                        EventIDService serv = new EventIDService(req); //call the eventIdService with the request to check the authentication
                        res1 = serv.post();
                        if (res1.getSuccess()) { //if the result of the service returns a success result, pass it to the client
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            HandlerPack.write(exchange, res1);
                        } else { //if there wasnt a success, pass the failed result with the failed message to the server
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            HandlerPack.write(exchange, res1);
                        }
                    } else { //if the args contains only one arguement, then it must be 'event'
                        EventService serv = new EventService(auth); //Call EventService to authenticate
                        res = serv.post();
                        if (res.getSuccess()) { //if successful return to client the success result
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            HandlerPack.write(exchange, res);
                        } else { //if not successful, return the result to the client
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
