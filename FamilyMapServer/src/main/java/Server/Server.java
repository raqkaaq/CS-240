package Server;

import Handler.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;
    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server");
        try{
            InetAddress ip = InetAddress.getLocalHost();
            server = HttpServer.create(
                    new InetSocketAddress(ip, Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);
        System.out.println("Creating contexts");
        //create contexts
        server.createContext("/clear", new ClearHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/fill/", new FillHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/", new DefaultFileHandler());

        System.out.println("Starting server");
        server.start();
        System.out.println("Server started on port " + portNumber);
        try {
            System.out.println("Server ip " + Inet4Address.getLocalHost());
        } catch (UnknownHostException e){
            e.printStackTrace();
            return;
        }
    }
    public static void main(String[] args){
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
//The Shared classes are stored in an external class called shared
