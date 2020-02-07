import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class HTTPEcho {
    public static void main( String[] args) throws IOException {
        // Your code here
        int portNumber = Integer.parseInt(args[0]);

        System.out.println("creating host on port: " + portNumber);

        ServerSocket server = new ServerSocket(portNumber);


        while(true) {
          Socket socket = server.accept();

          InputStream in = socket.getInputStream();

          String outputString = "";
          Integer lastByte = in.read();
          byte[] lastBytes = new byte[1];
          boolean lastCharNewLine = false;
          while(lastByte != -1) {
            if (new String(lastBytes, StandardCharsets.UTF_8).equals("\n")) {
              if(lastCharNewLine) {
                break;
                System.out.println("double newLine\nbreaking");

              }
              lastCharNewLine = true;
              System.out.println("lastCharNewLine set to true");

            } else {
              lastCharNewLine = false;
              System.out.println("lastCharNewLine set to false");
            }

            System.out.println(lastByte);

            lastBytes[0] = lastByte.byteValue();
            outputString = outputString + new String(lastBytes, StandardCharsets.UTF_8);
            lastByte = in.read();
          }

          byte[] output = outputString.getBytes(StandardCharsets.UTF_8);

          OutputStream out = socket.getOutputStream();
          out.write(output);

          socket.close();
        }




    }
}
