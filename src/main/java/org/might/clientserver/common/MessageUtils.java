package org.might.clientserver.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Date: 12/4/2023
 * Time: 11:18 AM
 */
public class MessageUtils {

    public static void sendMessage(Socket socket, String message) throws IOException {
        OutputStream stream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);
        objectOutputStream.writeUTF(message);
        objectOutputStream.flush();
    }
     public static String getMessage(Socket socket) throws IOException {
         InputStream stream = socket.getInputStream();
         ObjectInputStream objectInputStream = new ObjectInputStream(stream);
         return objectInputStream.readUTF();
     }
}