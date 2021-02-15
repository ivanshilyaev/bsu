package by.bsu.clientserver.bean;

import by.bsu.clientserver.service.Protocol;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;

public abstract class Message extends MessageXml implements Serializable {

    public static class Data implements Serializable {

        private static final long serialVersionUID = 1L;

        protected byte id;

        @XmlAttribute
        public byte getID() {
            return id;
        }

        public void setID(byte id) {
            assert (Protocol.validID(id));
            this.id = id;
        }

        public Data() {
        }

        public String toString() {
            return "" + id;
        }
    }

    private static final long serialVersionUID = 1L;

    protected abstract Message.Data getData();

    public byte getID() {
        return getData().getID();
    }

    protected Message() {
    }

    protected void setup(byte id) {
        getData().setID(id);
    }

    public String toString() {
        return getData().toString();
    }
}
