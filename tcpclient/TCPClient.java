package tcpclient;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TCPClient {



    private static String decodeInput(InputStream in) throws IOException {

      // temp buffer array
      // processes one byte at the time
      byte[] encodedBytes = new byte[1];

      Integer lastUsedByte = in.read();
      String decodedString = "";

      while(lastUsedByte != -1) {
        encodedBytes[0] = lastUsedByte.byteValue();

        decodedString = decodedString + new String(encodedBytes, StandardCharsets.UTF_8);
        lastUsedByte = in.read();

      }
      return decodedString;
    }

    public static String askServer(String hostname, int port, String ToServer) throws  IOException {

      // redirect to askServer/2
      // QUESTION: anyone aware of the way to document a method with n number of args?
      //           methodName/n is the elixir standard.
      if (ToServer == null) {
        return askServer(hostname, port);
      }

      Socket socket = new Socket(hostname, port);
      InputStream in = socket.getInputStream();
      OutputStream out = socket.getOutputStream();

      ToServer = ToServer + "\n";
      out.write(ToServer.getBytes());

      String decodedString = decodeInput(in);

      socket.close();

      return decodedString;
    }

    public static String askServer(String hostname, int port) throws  IOException {

      Socket socket = new Socket(hostname, port);
      InputStream in = socket.getInputStream();

      String decodedString = decodeInput(in);

      socket.close();

      return decodedString;
    }
}
