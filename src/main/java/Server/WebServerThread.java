package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class WebServerThread extends Thread implements Runnable {
    private Thread thread;
    private String threadName;
    private Socket clientSocket;

    public WebServerThread( Socket clientSocket,String threadname){this.clientSocket = clientSocket;this.threadName = threadname;}


    public void run() {

        // inetAddress.getlocalhos.getAddress
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line = reader.readLine();
            while (line!=null && !line.isEmpty()){
                System.out.println(line);
                line = reader.readLine();
            }
            //response
            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter aditional info:");
            String serverAdditionalInfo = "";
            OutputStream outputStream = clientSocket.getOutputStream();
            StringBuilder stringBuilder = new StringBuilder("HTTP/1.1. 200 OK\r\n\r\n <html><body><h1>Welcome to WebServer</h1><body><html>");
            outputStream.write(stringBuilder.toString().getBytes("UTF-8"));
            stringBuilder.delete(0,stringBuilder.length());
            while(!serverAdditionalInfo.equals("bye")) {
                serverAdditionalInfo = systemIn.readLine();
                stringBuilder.append("<html><body>\r\n\r\n<h2>S:" + serverAdditionalInfo + "</h2><body><html>");
                outputStream.write(stringBuilder.toString().getBytes("UTF-8"));
                stringBuilder.delete(0,stringBuilder.length());
            }
            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        System.out.println("Conecting client " + threadName);
        if (thread == null){
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    @Override
    protected void finalize() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close socket");
            System.exit(-1);
        }
    }
}
