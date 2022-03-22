package Handler;

import DataAccess.DataAccessException;
import Request.RegisterRequest;
import Result.RegisterResult;
import Result.Result;
import Server.Decode;
import Service.RegisterService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.Locale;

public class RegisterHandler implements HttpHandler {
    private RegisterResult res;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            if(exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")){
                Reader read = new InputStreamReader(exchange.getRequestBody()); //read register data
                RegisterRequest req = Decode.decodeRegisterRequest(read); //decode register data
                RegisterService serv = new RegisterService(req);
                serv.registerRun(); //generate register family tree
                res = serv.post();
            }
            if(res.getSuccess()){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                HandlerPack.write(exchange, res);
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                HandlerPack.write(exchange, res);
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            Result fail = new Result("Internal server error", false);
            HandlerPack.write(exchange, fail);
        } catch (DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            Result fail = new Result(e.getMessage(), false);
            HandlerPack.write(exchange, fail);
        }
    }
}
