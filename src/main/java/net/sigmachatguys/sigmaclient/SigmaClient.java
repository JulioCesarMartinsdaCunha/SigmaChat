package net.sigmachatguys.sigmaclient;

import net.sigmachatguys.guiscreen.SigmaMainConsole;
import net.sigmachatguys.messagemanage.SMessageManage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SigmaClient
{
    static Scanner scan = new Scanner(System.in);
    static Socket socketClient = null;
    static boolean connected = false;

    static String mainIp = "localhost";
    static int mainPort = 12345;

    public static void connect()
    {
        try
        {
            socketClient = new Socket(mainIp, mainPort);

            InputStreamReader isr = new InputStreamReader(socketClient.getInputStream());
            OutputStreamWriter osw = new OutputStreamWriter(socketClient.getOutputStream());
            BufferedReader br = new BufferedReader(isr);
            BufferedWriter bw = new BufferedWriter(osw);

            connected = true;

            Thread th = new Thread(){
                @Override
                public void run() {
                    super.run();

                    while(connected)
                    {
                        try
                        {
                            String msgRecebida = br.readLine();
                            System.out.println("Servidor: "+msgRecebida);
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                }
            };

            SMessageManage mainManage = SigmaMainConsole.getMessageManage();

            System.out.println("Conectado!");
            while(connected)
            {
                if(mainManage.isHaveNewMessage())
                {
                    String message = mainManage.getLastMessage().getMessage();
                    bw.write(message);
                    bw.newLine();
                    bw.flush();
                }
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void commandDisconnect()
    {

    }
}
