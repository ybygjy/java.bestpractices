package org.ybygjy.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Create the server
 * @author WangYanCheng
 * @version 2010-7-30
 */
public class CreateServer4InetSocket {
    /**httpServer*/
    private HttpServer httpServer = null;
    /**
     * 启动服务器
     * @param port 端口号
     */
    public void doStartServer(int port) {
        InetSocketAddress address = new InetSocketAddress(port);
        try {
            httpServer = HttpServer.create(address, 0);
            httpServer.createContext("/1234.xml", doCreateHttpHandler());
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * doCreate HttpHandler
     * @return httpHandlerInst
     * @throws IOException IOException
     */
    public HttpHandler doCreateHttpHandler() throws IOException {
        HttpHandler rtnHandler = new HttpHandler() {
            byte[] response =
                "<?xml version=\"1.0\"?>\n<resource id=\"HelloHttpServlet\" name=\"HelloWorld\"/>\n".getBytes();
            public void handle(HttpExchange exchange)
                throws IOException {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
                exchange.getResponseBody().write(response);
                exchange.close();
            }
        };
        return rtnHandler;
    }
    /**
     * doStopServer
     * @throws IOException IOException
     */
    public void doStopServer() throws IOException {
        if (null != this.httpServer) {
            this.httpServer.stop(0);
        }
    }
}
