package com.kita.lib.rpc;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

public class BEANRemoteExecution implements Externalizable {

    private static final long serialVersionUID = 1L;

    private String m_strClassName;
    private String m_strMethodName;
    private List m_lsMethodParameters;

    public BEANRemoteExecution() {}

    public BEANRemoteExecution(String p_strClassName, String p_strMethodName, List p_lsMethodParameters) {
        m_strClassName = p_strClassName;
        m_strMethodName = p_strMethodName;
        m_lsMethodParameters = p_lsMethodParameters;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(m_strClassName);
        out.writeUTF(m_strMethodName);
        out.writeObject(m_lsMethodParameters);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        m_strClassName = in.readUTF();
        m_strMethodName = in.readUTF();
        m_lsMethodParameters = (List) in.readObject();
    }

    public void setClassName(String p_strClassName) {
        m_strClassName = p_strClassName;
    }

    public String getMethodName() { return m_strMethodName; }

    public void setMethodName(String p_strMethodName) {
        m_strMethodName = p_strMethodName;
    }

    public List getMethodParameters() { return m_lsMethodParameters; }

    public void setMethodParameters(List p_lsMethodParameters) {
        p_lsMethodParameters = p_lsMethodParameters;
    }

    public String getClassName() { return m_strClassName; }
}
