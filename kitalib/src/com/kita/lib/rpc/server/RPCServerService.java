package com.kita.lib.rpc.server;

import com.kita.lib.rpc.BEANRemoteExecution;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RPCServerService implements Runnable {

    private Socket m_clientSocket;

    public RPCServerService(Socket p_clientSocket) {
        m_clientSocket = p_clientSocket;
    }

    public void run() {
        try {
            ObjectInputStream serverInputStream = new ObjectInputStream(m_clientSocket.getInputStream());

            BEANRemoteExecution beanRemoteExecution = (BEANRemoteExecution) serverInputStream.readObject();

            Object objReturn = CustomReflection.executeMethod(beanRemoteExecution.getClassName(), beanRemoteExecution.getMethodName(),
                    beanRemoteExecution.getMethodParameters());

            ObjectOutputStream out = new ObjectOutputStream(m_clientSocket.getOutputStream());
            out.writeObject(objReturn);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                m_clientSocket.close();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
