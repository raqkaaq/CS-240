package Handler;

import Request.PersonIDRequest;
import Result.PersonIDResult;
import Result.PersonResult;
import Result.Result;
import Service.PersonIDService;
import Service.PersonService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Locale;

public class PersonHandler implements HttpHandler {
    PersonIDResult res;
    PersonResult res1;
    String[] args;
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try{
            if(exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("get")){
                if(exchange.getRequestHeaders().containsKey("Authorization")){
                    String auth = exchange.getRequestHeaders().getFirst("Authorization"); //checks if the request requires authorization
                    String url = exchange.getRequestURI().toString();
                    args = HandlerPack.parseUrl(url);
                    if(args.length > 2 || args.length < 1){ //verifies the correct number of arguements
                        Result res = new Result("Invalid number of arguments", false);
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        HandlerPack.write(exchange, res);
                    } else if(args.length == 2){ //if there are two arguements the are 'person' and assumed to be a personid
                        PersonIDRequest req = new PersonIDRequest(args[1], auth); //check authorization and generate a request
                        PersonIDService serv = new PersonIDService(req);
                        res = serv.post();
                        if(res.getSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            HandlerPack.write(exchange, res);
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            HandlerPack.write(exchange, res);
                        }
                    } else if(args.length == 1){ //if there is only one arguement, it is 'person'
                        PersonService serv = new PersonService(auth); //check authorization and generate a request
                        res1 = serv.post();
                        if(res1.getSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            HandlerPack.write(exchange, res1);
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            HandlerPack.write(exchange, res1);
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
