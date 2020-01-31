package tcpclient;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TCPClient {

    public static String askServer(String hostname, int port, String ToServer) throws  IOException {

      if (ToServer == null) {
        return askServer(hostname, port);
      }

    return "n/a\n";
    }

    public static String askServer(String hostname, int port) throws  IOException {

      Socket socket = new Socket(hostname, port);
      InputStream os = socket.getInputStream();

      byte[] encodedBytes = new byte[51];

      // int i = 0;
      //
      // Integer currentByte = os.read();
      //
      // while(currentByte != -1) {
      //   // TODO: allocation does not work. move inside loop.
      //   encodedBytes[i++] = currentByte.byteValue();
      //
      //   System.out.println(new String(encodedBytes, StandardCharsets.UTF_8) + i);
      //
      //   // i++;
      // }

      int returnedBytes = os.read(encodedBytes);

      String decodedString = new String(encodedBytes, StandardCharsets.UTF_8);


      return decodedString + "\n";

    }
}
