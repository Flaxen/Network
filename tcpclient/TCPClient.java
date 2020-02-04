package tcpclient;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TCPClient {
    private static String decodeInput(InputStream in) throws IOException {

      String decodedString = "";
      try {
      // temp buffer array
      // processes one byte at the time
      byte[] encodedBytes = new byte[1];
      Integer lastUsedByte = in.read();
      int max = 8192;
      int count = 0;

        while(lastUsedByte != -1 && count++ < max) {

          encodedBytes[0] = lastUsedByte.byteValue();
          decodedString = decodedString + new String(encodedBytes, StandardCharsets.UTF_8);
          lastUsedByte = in.read();

        }
      } catch(SocketTimeoutException e) {
        // return decodedString;
      }
      return decodedString;
    }

    public static String askServer(String hostname, int port, String ToServer) throws  IOException {

      // redirect to askServer/2
      if (ToServer == null) {
        return askServer(hostname, port);
      }

      Socket socket = new Socket(hostname, port);
      InputStream in = socket.getInputStream();
      OutputStream out = socket.getOutputStream();

      ToServer = ToServer + "\n";
      out.write(ToServer.getBytes());

      socket.setSoTimeout(1000);
      String decodedString = decodeInput(in);

      socket.close();

      return decodedString;
    }

    public static String askServer(String hostname, int port) throws  IOException {

      Socket socket = new Socket(hostname, port);
      InputStream in = socket.getInputStream();

      socket.setSoTimeout(1000);
      String decodedString = decodeInput(in);

      socket.close();

      return decodedString;
    }
}
