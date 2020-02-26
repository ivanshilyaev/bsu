package by.bsu.clientserver.bean;

import by.bsu.clientserver.service.Protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public abstract class MessageXml implements Serializable {

    private static final long serialVersionUID = 1L;

    public MessageXml() {
    }

    public static void toXml( MessageXml msg, OutputStream os )
            throws JAXBException {
        JAXBContext context =
                JAXBContext.newInstance(msg.getClass());
        Marshaller m = context.createMarshaller();
        m.marshal( msg, os );
    }

    public static MessageXml fromXml( Class<? extends MessageXml> what,
                                      InputStream is ) throws JAXBException {
        JAXBContext context =
                JAXBContext.newInstance(what);
        Unmarshaller u = context.createUnmarshaller();
        return ( MessageXml ) u.unmarshal( is );
    }

    public static void writeMsg(DataOutputStream os, MessageXml msg)
            throws JAXBException, IOException {
        writeViaByteArray(os, msg);
    }

    public static MessageXml readMsg(DataInputStream is)
            throws JAXBException, IOException, ClassNotFoundException {
        return readViaByteArray(is);
    }

    public static void writeViaByteArray(DataOutputStream os, MessageXml msg)
            throws JAXBException, IOException {
        try(ByteArrayOutputStream bufOut = new ByteArrayOutputStream(512)) {
            try (DataOutputStream out = new DataOutputStream(bufOut)) {
                String name = msg.getClass().getName();
                out.writeUTF(name);
                MessageXml.toXml(msg, out);
                out.flush();
            }
            byte[] res = bufOut.toByteArray();
            os.writeInt(res.length);
            os.write(res);
            os.flush();
        }
    }

    @SuppressWarnings("unchecked")
    public static MessageXml readViaByteArray(DataInputStream is)
            throws JAXBException, IOException, ClassNotFoundException {
        int length = is.readInt();
        byte[] raw = new byte[length];
        int idx = 0, num = length;
        while ( idx < num ) {
            int n = is.read(raw, idx, num - idx);
            idx += n;
        }
        try (ByteArrayInputStream bufIn = new ByteArrayInputStream(raw)) {
            try (DataInputStream in = new DataInputStream(bufIn)) {
                String name = in.readUTF();
                return MessageXml.fromXml((Class<? extends MessageXml>)
                        Class.forName( name ), in);
            }
        }
    }

//    public static String lastQueryError = null;
//    public static boolean query2(Message msg, DataInputStream is, DataOutputStream os)
//            throws JAXBException, IOException {
//        // client
//        MessageContext ctx = new MessageContext(msg);
//        MessageXml.toXml(ctx, os);
//        os.flush();
//        MessageResult res = (MessageResult)
//                MessageXml.fromXml(MessageContextResult.class, is);
//        if ( res.Error()== false ) {
//            lastQueryError = null;
//            MessageXml.toXml(msg, os);
//            os.flush();
//            return true;
//        }
//        lastQueryError = res.getErrorMessage();
//        return false;
//    }

//    public static Class<? extends MessageResult> classResultById(byte id) {
//        assert( Protocol.validID( id )== true );
//        switch (id) {
//            case Protocol.CMD_CONTEXT:
//                return MessageContextResult.class;
//            case Protocol.CMD_CONNECT:
//                return MessageConnectResult.class;
//            case Protocol.CMD_USER:
//                return MessageUserResult.class;
//            case Protocol.CMD_CHECK_MAIL:
//                return MessageCheckMailResult.class;
//            case Protocol.CMD_LETTER:
//                return MessageLetterResult.class;
//            default:
//            case Protocol.CMD_DISCONNECT:
//                break;
//        }
//        return null;
//    }

//    public static Class<? extends Message> classById(byte id) {
//        assert(Protocol.validID(id));
//        switch (id) {
//            case Protocol.CMD_CONTEXT:
//                return MessageContext.class;
//            case Protocol.CMD_CONNECT:
//                return MessageConnect.class;
//            case Protocol.CMD_USER:
//                return MessageUser.class;
//            case Protocol.CMD_CHECK_MAIL:
//                return MessageCheckMail.class;
//            case Protocol.CMD_LETTER:
//                return MessageLetter.class;
//            case Protocol.CMD_DISCONNECT:
//                return MessageDisconnect.class;
//            default:
//                break;
//        }
//        return null;
//    }

//    public static MessageResult query(Message msg, DataInputStream is,
//                                      DataOutputStream os, Class<? extends MessageResult> what)
//            throws JAXBException, IOException {
//        // client
//        if ( query2( msg, is, os)) {
//            MessageResult res = (MessageResult) MessageXml.fromXml(what, is);
//            return res;
//        }
//        return null;
//    }

//    public static Message getMessage(DataInputStream is, DataOutputStream os)
//            throws JAXBException, ClassNotFoundException, IOException {
//        MessageContext ctx = (MessageContext)
//                MessageXml.fromXml(MessageContext.class, is);
//        Class<? extends Message> what = ctx.getXmlClass();
//        if ( what != null ) {
//            MessageXml.toXml( new MessageContextResult(), os);
//        }
//        else {
//            MessageXml.toXml( new MessageContextResult("Invalid XML-class ID"), os);
//        }
//        os.flush();
//        if ( what != null ) {
//            Message res = (Message) MessageXml.fromXml(what, is);
//            return res;
//        }
//        return null;
//    }

//    public static void answer(MessageResult msg,  DataOutputStream os)
//            throws JAXBException, IOException {
//        //server
//        MessageXml.toXml(msg, os);
//        os.flush();
//    }
}