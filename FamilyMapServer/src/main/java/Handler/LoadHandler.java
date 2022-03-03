package Handler;

import Request.LoadRequest;
import Result.LoadResult;
import Result.Result;
import Server.Decode;
import Service.ClearService;
import Service.LoadService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.Locale;

public class LoadHandler implements HttpHandler {
    LoadResult res;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            ClearService clear = new ClearService(); //clears tables before a load
            clear.clearTables();
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")) {
                Reader read = new InputStreamReader(exchange.getRequestBody()); //gets the load data
                LoadRequest req = Decode.decodeLoadRequest(read); //decodes the load data into a load request
                LoadService serv = new LoadService(req);
                res = serv.post();
            }
            if (res.getSuccess()) {
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
        }
    }
}
