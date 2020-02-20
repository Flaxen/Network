import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import tcpclient.TCPClient;

public class ConcHTTPAskTest {

    public static class HelloRunnable implements Runnable {
      int TID;
      public HelloRunnable(int TID) {
        this.TID = TID;
      }

      public void run() {
          System.out.printf("Hello from a thread nr %d!\n", TID);
      }



    }

    public static void main( String[] args) throws IOException {
      (new Thread(new HelloRunnable(1))).start();
      (new Thread(new HelloRunnable(2))).start();
      (new Thread(new HelloRunnable(3))).start();


    }
}
