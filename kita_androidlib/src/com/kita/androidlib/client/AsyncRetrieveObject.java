package com.kita.androidlib.client;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kita.lib.rpc.BEANRemoteExecution;
import com.kita.pettycash.client.interfaces.AsyncResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class AsyncRetrieveObject extends AsyncTask<Void, Void, Object> {
    public AsyncResponse delegate = null;

    private String HOST = null;
    private int PORT = 0;

    Context m_context;
    private String m_strClassName;
    private String m_strMethodName;
    private List<String> m_lsMethodParameters;

    ProgressBar m_prgBar;

    public AsyncRetrieveObject() {}

    public AsyncRetrieveObject(String p_host, int p_port, Context p_context, String p_strClassName, String p_strMethodName,
                               List<String> p_lsMethodParameters) {
        HOST = p_host;
        PORT = p_port;
        m_context = p_context;
        m_strClassName = p_strClassName;
        m_strMethodName = p_strMethodName;
        m_lsMethodParameters = p_lsMethodParameters;
    }

    public void setProgressbar(ProgressBar p_prgBar) {
        m_prgBar = p_prgBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        m_prgBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Object doInBackground(Void... voids) {
        Object objResult = null;

        try {
            BEANRemoteExecution remoteExecution = new BEANRemoteExecution(m_strClassName,
                    m_strMethodName, m_lsMethodParameters);

            Socket clientSocket = new Socket(HOST, PORT);

            ObjectOutputStream clientOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            clientOutputStream.writeObject(remoteExecution);
            clientOutputStream.flush();

            ObjectInputStream clientInputStream = new ObjectInputStream(clientSocket.getInputStream());
            objResult = clientInputStream.readObject();

            clientOutputStream.close();
            clientInputStream.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(m_context, "Error, please try again.", Toast.LENGTH_SHORT).show();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return objResult;
    }

    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        try {

            delegate.processFinish(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        m_prgBar.setVisibility(View.GONE);
    }
}
