package Handler;

import Request.LoginRequest;
import Result.LoginResult;
import Server.Decode;
import Server.Encode;
import Service.LoginService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.Locale;

public class LoginHandler implements HttpHandler {
    private LoginResult res;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            if(exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")){
                Reader read = new InputStreamReader(exchange.getRequestBody());
                LoginRequest req = Decode.decodeLoginRequest(read);
                LoginService serv = new LoginService(req);
                res = serv.post();
                if(res.getSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    HandlerPack.write(exchange, res);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    HandlerPack.write(exchange, res);
                }
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            HandlerPack.write(exchange, res);
        }
    }
}
