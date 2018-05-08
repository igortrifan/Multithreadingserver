package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8422);
        Socket socket;
        WebServerThread thread;
        int k = 1;

        while(true) {
            try{
                socket = serverSocket.accept();
                thread = new WebServerThread(socket, "Client-" + k++);
                thread.start();
            }
            catch(IOException e){
                System.out.println("Server failed");
                System.exit(-1);
            }

        }
    }
}
