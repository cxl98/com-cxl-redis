package com.cxl.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisServer {
    private static final Logger LOGGER= LoggerFactory.getLogger(RedisServer.class);
    private int port;
    public void init(int port) throws IOException {
        this.port=port;
        run();
    }
    private void run() throws IOException {
        ServerSocket serverSocket=new ServerSocket(port);
        LOGGER.info(">>>等待连接..."+serverSocket.getInetAddress());
        ExecutorService service= Executors.newSingleThreadExecutor();
        while(true){
            Socket client=serverSocket.accept();
            LOGGER.info(">>>>客户端连接成功....."+client.getRemoteSocketAddress());
            service.execute(new MultiThread(client));
        }
    }
}
