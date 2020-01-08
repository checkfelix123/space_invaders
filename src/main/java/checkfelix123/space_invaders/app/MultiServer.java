 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkfelix123.space_invaders.app;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felix
 */
public class MultiServer extends java.lang.Thread {

    private static boolean running;
    private Socket s = null;

    public MultiServer(Socket s) {
        this.s = s;
    }

    public static void main(String args[]) {
        try (ServerSocket listen = new ServerSocket(4711)) {
            listen.setSoTimeout(2000);
            System.out.println("Server is running: " + listen);
            running = true;
            new Console().start();
            while (running) {
                try {
                    new MultiServer(listen.accept()).start();
                } catch (SocketTimeoutException e) {
// keine Aktion notwendig
                    
                }
            }
        } catch (IOException ioe) {
            System.out.println("Fehler:" + ioe.toString());
        }
    }

    @Override
    public void run() {
        try(InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
                ){
           DataInputStream dis = new DataInputStream(in);
            
         
            System.out.println("new Connection " + s);
            // System.out.println(s.get);
               System.out.println(dis.readInt());
                
    }   catch (IOException ex) {
            Logger.getLogger(MultiServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
// Innere Klasse zur Serversteuerung

    static class Console extends Thread {

        @Override
        public void run() {
            Scanner sc = new Scanner(System.in);
            String cmd;
            System.out.print("> ");
            while ((cmd = sc.nextLine()) != null && !"quit".equals(cmd)) {
//---------------------------
// cmd verarbeiten

            }
//---------------------------
            System.out.print("> ");
            running=false;
        }
      
    }
}
