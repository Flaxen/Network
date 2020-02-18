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
          System.out.println("Awaiting Client");
          Socket socket = server.accept();
          System.out.println("Client accepted");

          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          String outputString = "";
          String lastLine = in.readLine();

          while(lastLine != null) {
            System.out.println("LastLine: " + lastLine);
            if(lastLine.equals("")) {
              break;
            }

            outputString = outputString + lastLine + "\r\n";
            lastLine = in.readLine();
          }

          outputString = "HTTP/1.1 200 OK\r\n\r\n" + outputString;

          byte[] output = outputString.getBytes(StandardCharsets.UTF_8);

          OutputStream out = socket.getOutputStream();
          out.write(output);

          socket.close();
        }




    }
}
