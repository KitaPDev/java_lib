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

public class AsyncUserExists extends AsyncTask<Void, Void, Boolean> {
    public AsyncResponse delegate = null;

    String HOST;
    int PORT;

    Context m_context;
    String m_strClassName;
    String m_strMethodName;
    List<String> m_lsMethodParameters;

    ProgressBar m_prgBar;

    public AsyncUserExists() {}

    public AsyncUserExists(String p_HOST, int p_PORT, Context p_context, String p_strClassName, String p_strMethodName, List<String> p_lsMethodParameters) {
        HOST = p_HOST;
        PORT = p_PORT;
        m_context = p_context;
        m_strClassName = p_strClassName;
        m_strMethodName = p_strMethodName;
        m_lsMethodParameters = p_lsMethodParameters;
    }

    public void setProgressBar(ProgressBar p_prgBar) {
        m_prgBar = p_prgBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        m_prgBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean isExistingUser = null;

        try {
            BEANRemoteExecution remoteExecution = new BEANRemoteExecution(m_strClassName,
                    m_strMethodName, m_lsMethodParameters);

            Socket clientSocket = new Socket(HOST, PORT);

            ObjectOutputStream clientOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            clientOutputStream.writeObject(remoteExecution);
            clientOutputStream.flush();

            ObjectInputStream clientInputStream = new ObjectInputStream(clientSocket.getInputStream());
            isExistingUser = clientInputStream.readBoolean();

            clientOutputStream.close();
            clientInputStream.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(m_context, "Error, please try again.", Toast.LENGTH_SHORT).show();

        }

        return isExistingUser;
    }

    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        m_prgBar.setVisibility(View.GONE);

        try {

            delegate.processFinish(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
