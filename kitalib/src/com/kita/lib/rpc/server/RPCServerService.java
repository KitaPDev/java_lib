package com.kita.lib.rpc.server;

import com.kita.lib.rpc.BEANRemoteExecution;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RPCServerService implements Runnable {

    private Socket m_clientSocket;

    public RPCServerService(Socket p_clientSocket) {
        m_clientSocket = p_clientSocket;
    }

    public void run() {
        try {
            System.out.println("Retrieving socket data . . .");
//            ObjectInputStream serverInputStream = new ObjectInputStream(m_clientSocket.getInputStream());
//
//            BEANRemoteExecution beanRemoteExecution = (BEANRemoteExecution) serverInputStream.readObject();
//
//            System.out.println("Executing " + beanRemoteExecution.getMethodName() + " method at " + beanRemoteExecution.getClassName() +
//                    " class");
//            Object objReturn = CustomReflection.executeMethod(beanRemoteExecution.getClassName(), beanRemoteExecution.getMethodName(),
//                    beanRemoteExecution.getMethodParameters());
//
//            ObjectOutputStream out = new ObjectOutputStream(m_clientSocket.getOutputStream());
////            out.writeObject(objReturn);
////            out.flush();

            ObjectInputStream ois = new ObjectInputStream(m_clientSocket.getInputStream());
            String strInputStream = ois.readUTF();

            BEANRemoteExecution beanRemoteExecution = new BEANRemoteExecution();
            Scanner scanner = new Scanner(strInputStream);

            if(strInputStream.length() > 1) {
                beanRemoteExecution.setClassName(scanner.next());
                beanRemoteExecution.setMethodName(scanner.next());

                List lsMethodParameters = new ArrayList();
                while(scanner.hasNext()) {
                    lsMethodParameters.add(scanner.next());
                }

                beanRemoteExecution.setMethodParameters(lsMethodParameters);
            }
            System.out.println("Executing " + beanRemoteExecution.getMethodName() + " method at " + beanRemoteExecution.getClassName() +
                    " class");

            Object objReturn = CustomReflection.executeMethod(beanRemoteExecution.getClassName(), beanRemoteExecution.getMethodName(),
                    beanRemoteExecution.getMethodParameters());

            ObjectOutputStream out = new ObjectOutputStream(m_clientSocket.getOutputStream());
            out.writeObject(objReturn);
            out.flush();

            System.out.println("Result sent to client");

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
