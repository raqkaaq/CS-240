package Proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ProxyPack {
    public static String readString(InputStream in) throws IOException {
        StringBuilder s = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(in);
        char[] buffer = new char[1024];
        int len;
        while((len = sr.read(buffer)) > 0){
            s.append(buffer, 0, len);
        }
        return s.toString();
    }
    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(os);
        out.write(str);
        out.flush();
    }
}
