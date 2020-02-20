import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import tcpclient.TCPClient;

public class HTTPAsk {


    static String[] processString(String inputString) {
      String[] parameters = new String[2];
      String[] errorString = new String[1];

      if (!inputString.startsWith("GET /ask?hostname=") || !inputString.contains("&port=")) {
        errorString[0] = "400 Bad Request\r\n\r\n";
        return errorString;
      }

      inputString = inputString.replace("GET /ask?", "");
      System.out.println(inputString);

      parameters = inputString.split("&");

      for(int i = 0; i < parameters.length; i++) {
        System.out.println(parameters[i]);
      }

      if(parameters.length >= 2) {
        parameters[0] = parameters[0].replace("hostname=", "");
        parameters[1] = parameters[1].replace("port=", "");
        parameters[1] = parameters[1].split(" HTTP")[0];
      }
      if(parameters.length == 3) {

        if(!parameters[2].startsWith("string=")){
          errorString[0] = "400 Bad Request\r\n\r\n";
          return errorString;
        }
        parameters[2] = parameters[2].replace("string=", "");
        parameters[2] = parameters[2].split(" HTTP")[0];
      }

      if(parameters.length > 3) {
        errorString[0] = "400 Bad Request\r\n\r\n";
        return errorString;
      }


      for(int i = 0; i < parameters.length; i++) {
        System.out.println(parameters[i] + "end");
      }

      return parameters;
    }


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
          String outputString = "HTTP/1.1 200 OK\r\n\r\n";
          String inputString = "";
          String lastLine = in.readLine();

          if(lastLine != null) {
            System.out.println("LastLine: " + lastLine);
            inputString = inputString + lastLine + "\r\n";
            lastLine = in.readLine();
          }

          String[] parameters = processString(inputString);

          try{

            if(parameters.length == 1) {
              outputString = parameters[0];
            } else if(parameters.length == 2) {
              outputString = outputString + TCPClient.askServer(parameters[0], Integer.parseInt(parameters[1]));
            } else if(parameters.length == 3) {

              outputString = outputString + TCPClient.askServer(parameters[0], Integer.parseInt(parameters[1]), parameters[2]);

            } else {
              System.out.println("Error: parameter len not 2 or 3");
            }

          } catch(SocketTimeoutException ex) {
            outputString = "408 Request Timeout\r\n\r\n";
          } catch(UnknownHostException ex) {
            outputString = "404 Not Found\r\n\r\n";
          }


          byte[] output = outputString.getBytes(StandardCharsets.UTF_8);
          OutputStream out = socket.getOutputStream();
          out.write(output);

          socket.close();

        }
    }
}
