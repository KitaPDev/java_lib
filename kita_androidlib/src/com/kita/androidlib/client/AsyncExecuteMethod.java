package com.kita.androidlib.client;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kita.lib.rpc.BEANRemoteExecution;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class AsyncExecuteMethod extends AsyncTask<String, Void, Void> {

    private String m_HOST;
    private int m_PORT;
    private String m_strClassName;
    private String m_strMethodName;
    private List m_lsMethodParameters;

    private Object m_objResult;

    private ProgressBar prgBar;

    public AsyncExecuteMethod() {}

    public AsyncExecuteMethod(ProgressBar prgBar, String p_HOST, int p_PORT, String p_strClassName, String p_strMethodName, List p_lsMethodParameters) {
        this.prgBar = prgBar;
        m_HOST = p_HOST;
        m_PORT = p_PORT;
        m_strClassName = p_strClassName;
        m_strMethodName = p_strMethodName;
        m_lsMethodParameters = p_lsMethodParameters;
    }

    protected void onPreExecute() {
        super.onPreExecute();

        prgBar.setVisibility(View.VISIBLE);
    }


    @Override
    protected Void doInBackground(String... strings) {

        try {
            BEANRemoteExecution remoteExecution = new BEANRemoteExecution(m_strClassName,
                    m_strMethodName, m_lsMethodParameters);

            Socket clientSocket = new Socket(m_HOST, m_PORT);

            ObjectOutputStream clientOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            clientOutputStream.writeObject(remoteExecution);
            clientOutputStream.flush();

            ObjectInputStream clientInputStream = new ObjectInputStream(clientSocket.getInputStream());
            m_objResult = clientInputStream.read();

            clientInputStream.close();
            clientOutputStream.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        prgBar.setVisibility(View.GONE);
    }

    public String getClassName() {
        return m_strClassName;
    }

    public void setClassName(String p_strClassName) {
        m_strClassName = p_strClassName;
    }

    public String getMethodName() {
        return m_strMethodName;
    }

    public void setMethodName(String p_strMethodName) {
        m_strMethodName = p_strMethodName;
    }

    public List getMethodParameters() {
        return m_lsMethodParameters;
    }

    public void setMethodParameters(List p_lsMethodParameters) {
        m_lsMethodParameters = p_lsMethodParameters;
    }

    public String getHost() {
        return m_HOST;
    }

    public void setHost(String p_HOST) {
        m_HOST = p_HOST;
    }

    public int getPort() {
        return m_PORT;
    }

    public void setPort(int p_PORT) {
        m_PORT = p_PORT;
    }

    public Object getResult() {
        return m_objResult;
    }

    public void setResult(Object p_objResult) {
        m_objResult = p_objResult;
    }
}
