package com.kita.lib.rpc.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class RPCServer {

    private static Map<String, Class> s_mapHandler_Name = new TreeMap<>();

    public RPCServer() {}

    public static void registerHandler(Class p_classHandler) {
        s_mapHandler_Name.put(p_classHandler.getSimpleName(), p_classHandler);
    }

    public static Class getHandler(String p_strHandlerName) throws IOException {
        if(s_mapHandler_Name.containsKey(p_strHandlerName)) {
            return s_mapHandler_Name.get(p_strHandlerName);
        }

        throw new IOException("Handler not found (" + p_strHandlerName + ").");
    }

    public void startServer(int p_intPort) {
        try {
            ServerSocket serverSocket = new ServerSocket(p_intPort);

            while (true) {
                System.out.println("PettyCashServer Waiting . . .");
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                RPCServerService rpcServerService = new RPCServerService(clientSocket);

                System.out.println("Assigning thread for client.");
                Thread thread = new Thread(rpcServerService);
                thread.start();
            }

        } catch(Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
