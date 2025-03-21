package net.sigmachatguys.sigmaserve;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SigmaServe
{
    static ServerSocket serveSocket = null;
    static Socket socketClient = null;

    static boolean serveOn = false;
    static boolean chatting = false;

    static Scanner scan = new Scanner(System.in);

    static int mainPort = 12345;

    public static void initializeServe()
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(mainPort);
            System.out.println("Servidor inicializado!");
            while(true)
            {
                socketClient = serverSocket.accept();
                InputStreamReader isr = new InputStreamReader(socketClient.getInputStream());
                OutputStreamWriter osw = new OutputStreamWriter(socketClient.getOutputStream());
                BufferedReader br = new BufferedReader(isr);
                BufferedWriter bw = new BufferedWriter(osw);

                chatting = true;

                Thread th = new Thread(){
                    @Override
                    public void run()
                    {
                        while (chatting)
                        {
                            try
                            {
                                String msgRecebida = br.readLine();
                                System.out.println("Cliente: "+msgRecebida);


                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                };
                th.start();

                System.out.println("Cliente connectado!");
                while(chatting)
                {
                    String msgEnviar = scan.nextLine();

                    if(msgEnviar.equalsIgnoreCase("COMANDO-EXPULSAR"))
                    {
                        bw.write("Você foi expulso do chat!");
                        bw.newLine();
                        bw.flush();
                        chatting = false;
                        break;
                    }

                    bw.write(msgEnviar);
                    bw.newLine();
                    bw.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopServe()
    {

    }
}
