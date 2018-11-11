package com.kita.lib.rpc;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class BEANRemoteReturn implements Externalizable {

    private Object m_ReturnObject;

    public BEANRemoteReturn() {}

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(m_ReturnObject);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        m_ReturnObject = in.readObject();
    }

    public Object getReturnObject() { return m_ReturnObject; }
    public void setReturnObject(Object p_ReturnObject) { m_ReturnObject = p_ReturnObject; }
}
