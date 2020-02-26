package by.bsu.clientserver.bean.mymessage;

import by.bsu.clientserver.bean.MessageXml;

import java.io.Serializable;

public class Message extends MessageXml implements Serializable {
    private MessageType type;
    private String data;
    private byte[] file;

    public Message(MessageType type) {
        this.type = type;
        data = null;
        file = null;
    }

    public Message(MessageType type, String data) {
        this.type = type;
        this.data = data;
        file = null;
    }

    public Message(MessageType type, String data, byte[] file) {
        this.type = type;
        this.data = data;
        this.file = file;
    }

    public MessageType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public byte[] getFile() {
        return file;
    }
}
